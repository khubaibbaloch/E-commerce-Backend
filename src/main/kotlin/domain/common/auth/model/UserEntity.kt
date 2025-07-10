package com.commerce.domain.common.auth.model

/**
 * Domain model representing a user during authentication operations.
 *
 * This model is used internally within the application to handle
 * login and registration logic before being persisted or transformed.
 *
 * @property username The unique username used for user identification.
 * @property password The plaintext or hashed password used for authentication.
 */
data class UserEntity(
    val username: String,
    val password: String
)
