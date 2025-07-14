package data.common.auth.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) for verifying a user's email using an OTP code.
 *
 * This request is used when the user enters the OTP received via email to verify their identity.
 *
 * @property userId The unique ID of the user attempting verification.
 * @property otp The one-time password/token sent to the user's email.
 */
@Serializable
data class VerifyOtpRequest(
    val userId: String,
    val otp: String
)
