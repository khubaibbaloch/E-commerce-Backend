package com.commerce.domain.common.auth.service

import com.commerce.domain.common.auth.model.UserEntity

/**
 * Service interface for handling authentication-related business logic.
 * This sits between the controller layer and repository layer.
 */
interface AuthService {

    /**
     * Registers a new user and returns the generated user ID.
     *
     * @param userEntity The user details to register.
     * @return The newly created user's ID as a [String], or null if registration fails.
     */
    suspend fun registerAndReturnUserId(userEntity: UserEntity): String?

    /**
     * Logs in a user by verifying their credentials.
     *
     * @param userEntity The user credentials (username and password).
     * @return A JWT token as a [String] if authentication is successful, or null otherwise.
     */
    suspend fun login(userEntity: UserEntity): String?
}
