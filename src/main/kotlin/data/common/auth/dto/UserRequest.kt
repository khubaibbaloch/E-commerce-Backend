package com.commerce.data.common.auth.dto

import kotlinx.serialization.Serializable

/**
 * DTO for user login or registration requests.
 * Used to capture the basic credentials required for authentication.
 */
@Serializable
data class UserRequest(
    val username: String,  // The username provided by the client
    val password: String   // The corresponding password (should be hashed/stored securely)
)
