package data.admin.dto

import kotlinx.serialization.Serializable

/**
 * DTO for Admin view of users.
 * Provides access to user credentials and database references.
 */
@Serializable
data class UserAdminResponse(
    val id: Int,             // Internal database ID (primary key)
    val userid: String,      // Public or UUID user identifier
    val username: String,    // User's login name
    val password: String     // User's hashed password (Note: avoid exposing raw passwords in production)
)
