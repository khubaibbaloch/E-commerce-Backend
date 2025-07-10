package com.commerce.domain.admin.model

/**
 * Domain entity representing a user with full admin-level visibility.
 * This includes sensitive information such as passwords (hashed or encrypted).
 * Intended for use in admin-specific operations like user management and audits.
 */
data class UserAdminEntity(
    val id: Int,             // Auto-incremented primary key in the database (internal reference)
    val userid: String,      // Public/system-wide unique identifier (UUID) for the user
    val username: String,    // Unique username used for authentication
    val password: String     // User's password (should be stored as a secure hash)
)
