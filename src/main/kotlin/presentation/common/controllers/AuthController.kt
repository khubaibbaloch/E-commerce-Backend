package com.commerce.presentation.common.controllers

import com.commerce.config.JwtConfig
import com.commerce.data.common.auth.dto.UserRequest
import com.commerce.data.common.auth.mapper.toDomain
import com.commerce.domain.common.auth.usecase.AuthUseCase
import data.common.auth.dto.EmailVerificationRequest
import data.common.auth.dto.VerifyOtpRequest
import domain.common.auth.model.RegisterResult
import domain.common.auth.model.UserRole
import io.ktor.server.application.*
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import utils.fetchGoogleUserInfo
import utils.generateOTP
import utils.sendEmail
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.String

/**
 * Controller class responsible for handling authentication-related operations.
 * Handles user registration (sign up), role-based login, and Google OAuth login.
 *
 * @param authUseCase Contains use cases for registering and logging in a user.
 */
class AuthController(private val authUseCase: AuthUseCase) {

    /**
     * Handles user registration using email and password.
     * If user is new → creates user and sends verification email.
     * If user exists → checks if already verified, otherwise resends verification.
     */
    suspend fun signUp(call: ApplicationCall) {
        val request = call.receive<UserRequest>()
        val user = request.toDomain()
        val result = authUseCase.registerUseCase(user)

        when (result) {
            is RegisterResult.NewUser -> {
                val sendResult = sendEmailWithLink(email = request.email, userId = result.userId)

                if (sendResult.isBlank()) {
                    call.respond(
                        HttpStatusCode.OK, mapOf(
                            "message" to "Account created, but failed to send verification email.",
                            "userId" to result.userId
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.OK, mapOf(
                            "message" to "Account created. A verification email has been sent. Please check your inbox.",
                            "userId" to result.userId
                        )
                    )
                }
            }

            is RegisterResult.ExistingUser -> {
                val existing = authUseCase.getEmailVerificationUseCase(result.userId)

                if (existing == null) {
                    call.respond(
                        HttpStatusCode.Conflict, mapOf(
                            "error" to "User exists but verification details are missing. Please contact support or try again.",
                            "userId" to result.userId
                        )
                    )
                    return
                }

                if (existing.verified) {
                    call.respond(
                        HttpStatusCode.Conflict, mapOf(
                            "error" to "User already exists. Please log in.",
                            "userId" to result.userId
                        )
                    )
                } else {
                    val resendResult = sendEmailWithLink(email = existing.email, userId = existing.userId)

                    if (resendResult.isBlank()) {
                        call.respond(
                            HttpStatusCode.Conflict, mapOf(
                                "error" to "User exists but verification failed to resend. Please try again later.",
                                "userId" to result.userId
                            )
                        )
                    } else {
                        call.respond(
                            HttpStatusCode.Conflict, mapOf(
                                "message" to "User exists but is not verified. A new verification email has been sent.",
                                "userId" to result.userId
                            )
                        )
                    }
                }
            }
        }

    }


    /**
     * Handles role-based login for User/Seller/Admin.
     * - Verifies credentials
     * - Sends verification email if not verified
     * - Returns JWT token if login succeeds
     */
    suspend fun login(call: ApplicationCall, role: UserRole) {
        val request = call.receive<UserRequest>()
        val user = request.toDomain()

        val userId = authUseCase.loginUseCase(user)

        if (userId == null) {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
            return
        }

        val existing = authUseCase.getEmailVerificationUseCase(userId)

        if (existing?.verified != true) {
            // Always resend OTP, regardless of existing record
            val emailToUse = existing?.email ?: request.email
            val resendResult = sendEmailWithLink(email = emailToUse, userId = userId)

            if (resendResult.isBlank()) {
                call.respond(
                    HttpStatusCode.Forbidden,
                    mapOf(
                        "error" to "Email not verified. Failed to send verification email.",
                        "userId" to userId
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.Forbidden,
                    mapOf(
                        "message" to "Email not verified. A new verification email has been sent.",
                        "userId" to userId
                    )
                )
            }
            return
        }

        // ✅ All good — verified + credentials valid
        val token = JwtConfig.generateToken(userId, role)
        call.respond(HttpStatusCode.OK, mapOf("token" to token, "userId" to userId))
    }


    /**
     * Handles OAuth sign-in/signup via Google.
     * - Gets token from Google
     * - Fetches user profile
     * - TODO: Add DB login/registration logic
     */
    suspend fun googleSignUp(call: ApplicationCall) {
        val principal = call.principal<OAuthAccessTokenResponse.OAuth2>()
        val accessToken = principal?.accessToken

        if (accessToken == null) {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "OAuth failed."))
            return
        }

        try {
            // Fetch Google user info using reusable HttpClient
            val userInfo = fetchGoogleUserInfo(accessToken)

            // TODO: Check DB if user exists → If not, register → If exists, return token
            // val jwt = JwtConfig.generateToken(userInfo.id, UserRole.USER)
            // call.respond(HttpStatusCode.OK, mapOf("token" to jwt))

            // Temporary response (raw Google user info)
            call.respond(HttpStatusCode.OK, userInfo)

        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Failed to fetch user info")
            )
        }
    }

    /**
     * Sends OTP via email and saves it in database.
     * Returns userId if success, else blank.
     */

    suspend fun sendEmailWithOtp(email: String, userId: String): String {
        val otp = generateOTP()
        val expiry = LocalDateTime.now().plusMinutes(15).toString()

        val emailVerificationRequest = EmailVerificationRequest(
            userId = userId,
            email = email,
            tokenOrOtp = otp,
            expiresAt = expiry,
            verified = false
        )
        val domainModel = emailVerificationRequest.toDomain()

        val success = sendEmail(
            to = email,
            subject = "Email verification",
            htmlContent = """
            <h2>Verify your email</h2>
            <p>Your OTP is: <b>$otp</b></p>
        """.trimIndent()
        )
        return if (success) {
            authUseCase.upsertEmailVerificationUseCase(domainModel)
        } else {
            "" // signal failure to `signUp()`
        }
    }

    /**
     * Verifies the email using OTP code entered by user.
     */
    suspend fun verifyEmailWithOtp(call: ApplicationCall) {
        val request = call.receive<VerifyOtpRequest>()

        if (request.otp.isBlank() || request.userId.isBlank()) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "OTP and User ID must not be empty.")
            )
            return
        }

        val existing = authUseCase.getEmailVerificationUseCase(request.userId)

        if (existing == null) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Verification entry not found. Please request a new OTP.")
            )
            return
        }

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val expirationTime = LocalDateTime.parse(existing.expiresAt, formatter)
        val now = LocalDateTime.now()

        if (now.isAfter(expirationTime)) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "OTP has expired.")
            )
            return
        }

        if (existing.tokenOrOtp.trim() == request.otp.trim()) {
            val wasUpdated = authUseCase.markEmailAsVerifiedUseCase(request.userId)

            if (wasUpdated) {
                call.respond(
                    HttpStatusCode.OK,
                    mapOf("message" to "User verification completed, now you can login.")
                )
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Failed to update verification status. Please try again.")
                )
            }
            return
        }

        call.respond(
            HttpStatusCode.BadRequest,
            mapOf("error" to "Invalid OTP. Please try again.")
        )
    }

    /**
     * Sends a verification link via email (with UUID token).
     */
    suspend fun sendEmailWithLink(email: String, userId: String): String {
        val token = UUID.randomUUID().toString()
        val expiry = LocalDateTime.now().plusMinutes(15).toString()

        val verificationUrl = "http://127.0.0.1:8080/auth/emailVerification?userId=$userId&token=$token"

        val emailVerificationRequest = EmailVerificationRequest(
            userId = userId,
            email = email,
            tokenOrOtp = token,
            expiresAt = expiry,
            verified = false
        )
        val domainModel = emailVerificationRequest.toDomain()

        val success = sendEmail(
            to = email,
            subject = "Verify Your Email Address",
            htmlContent = """
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto;">
            <h2 style="color: #333;">Welcome to Our App!</h2>
            <p>Thank you for signing up. Please verify your email address by clicking the button below:</p>
            
            <p style="text-align: center;">
                <a href="$verificationUrl" style="background-color: #4CAF50; color: white; padding: 12px 20px; text-decoration: none; border-radius: 4px; display: inline-block;">
                    Verify Email
                </a>
            </p>

            <p>If the button doesn't work, you can also copy and paste the following link into your browser:</p>
            <p style="word-wrap: break-word;">$verificationUrl</p>

            <p style="color: #888; font-size: 12px;">This link will expire in 15 minutes.</p>
        </div>
    """.trimIndent()
        )


        return if (success) {
            authUseCase.upsertEmailVerificationUseCase(domainModel)
        } else {
            "" // signal failure
        }
    }

    /**
     * Verifies user email when user clicks on verification link.
     */
    suspend fun verifyEmailWithLink(call: ApplicationCall) {
        val userId = call.request.queryParameters["userId"] ?: ""
        val token = call.request.queryParameters["token"] ?: ""


        if (token.isBlank() || userId.isBlank()) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "OTP and User ID must not be empty.")
            )
            return
        }

        val existing = authUseCase.getEmailVerificationUseCase(userId)

        if (existing == null) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Verification entry not found. Please request a new OTP.")
            )
            return
        }

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val expirationTime = LocalDateTime.parse(existing.expiresAt, formatter)
        val now = LocalDateTime.now()

        if (now.isAfter(expirationTime)) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Link has expired.")
            )
            return
        }

        if (existing.tokenOrOtp.trim() == token.trim() && existing.userId == userId) {
            val wasUpdated = authUseCase.markEmailAsVerifiedUseCase(userId)

            if (wasUpdated) {
                call.respond(
                    HttpStatusCode.OK,
                    mapOf("message" to "User verification completed, now you can login.")
                )
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Failed to update verification status. Please try again.")
                )
            }
            return
        }

        call.respond(
            HttpStatusCode.BadRequest,
            mapOf("error" to "Invalid OTP. Please try again.")
        )
    }
}

