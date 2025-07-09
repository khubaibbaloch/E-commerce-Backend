package com.commerce.data.common.auth.dto

import kotlinx.serialization.Serializable

/**
 * DTO for user-related operations where a user ID is also required.
 * Typically used in internal admin APIs or user updates.
 */
@Serializable
data class UserRequestWithId(
    val userId: String,    // Unique identifier of the user (e.g., UUID or database ID)
    val username: String,  // Username associated with the account
    val password: String   // Password (ensure it's handled securely in service layer)
)
