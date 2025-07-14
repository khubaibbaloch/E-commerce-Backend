package data.common.auth.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) for handling email verification.
 *
 * This DTO is used to pass email verification data between layers,
 * including OTP or token-based verification logic.
 *
 * @property userId The unique ID of the user.
 * @property email The email address to be verified.
 * @property tokenOrOtp The verification token or OTP sent to the user.
 * @property expiresAt The expiration time of the token or OTP (as a string).
 * @property verified Indicates whether the email has been verified.
 */
@Serializable
data class EmailVerificationRequest(
    val userId : String,
    val email : String,
    val tokenOrOtp : String,
    val expiresAt : String,
    val verified : Boolean
)
