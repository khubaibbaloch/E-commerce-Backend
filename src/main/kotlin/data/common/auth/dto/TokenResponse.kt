package com.commerce.data.common.auth.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) for sending JWT tokens to the client.
 * This is typically returned after successful authentication or role upgrade.
 */
@Serializable
data class TokenResponse(
    val token: String // The JWT token to be used for authenticated API access
)
