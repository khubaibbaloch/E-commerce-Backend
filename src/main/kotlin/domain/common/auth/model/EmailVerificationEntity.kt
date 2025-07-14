package com.commerce.domain.common.auth.model

/**
 * Domain model representing an email verification record.
 *
 * This entity holds all relevant information required to verify a user's email,
 * including token/OTP, expiration time, and verification status.
 *
 * @property userId The unique identifier of the user.
 * @property email The email address to be verified.
 * @property tokenOrOtp The token or one-time password used for verification.
 * @property expiresAt The expiration timestamp of the token/OTP in ISO 8601 format.
 * @property verified Indicates whether the email has been successfully verified.
 */
data class EmailVerificationEntity(
    val userId: String,
    val email: String,
    val tokenOrOtp: String,
    val expiresAt: String,
    val verified: Boolean
)
