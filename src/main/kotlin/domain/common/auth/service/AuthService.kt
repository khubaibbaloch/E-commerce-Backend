package com.commerce.domain.common.auth.service

import com.commerce.domain.common.auth.model.EmailVerificationEntity
import com.commerce.domain.common.auth.model.UserEntity
import domain.common.auth.model.RegisterResult

/**
 * Service interface for handling authentication and email verification business logic.
 *
 * This layer acts as an abstraction between the controller (presentation) and repository (data access) layers.
 */
interface AuthService {

    /**
     * Registers a new user and returns the generated user ID.
     *
     * @param userEntity The user details to register.
     * @return A [RegisterResult] representing whether the user is newly created or already exists.
     */
    suspend fun registerAndReturnUserId(userEntity: UserEntity): RegisterResult

    /**
     * Inserts or updates the email verification record (OTP or link) for a user.
     *
     * @param entity The email verification details (user ID, email, token/OTP, etc.).
     * @return The associated user ID on success.
     */
    suspend fun upsertEmailVerification(entity: EmailVerificationEntity): String

    /**
     * Authenticates a user by verifying credentials (username/email and password).
     *
     * @param userEntity The user credentials for login.
     * @return The user's unique ID as a [String] if credentials match; otherwise, null.
     */
    suspend fun login(userEntity: UserEntity): String?

    /**
     * Retrieves email verification details for the specified user.
     *
     * @param userId The unique identifier of the user.
     * @return The [EmailVerificationEntity] if found, else null.
     */
    suspend fun getEmailVerification(userId: String): EmailVerificationEntity?

    /**
     * Marks a user's email as verified.
     *
     * @param userId The unique identifier of the user.
     * @return True if the verification status was updated; false otherwise.
     */
    suspend fun markEmailAsVerified(userId: String): Boolean
}
