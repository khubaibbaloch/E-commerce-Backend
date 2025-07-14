package com.commerce.domain.common.auth.repository

import com.commerce.domain.common.auth.model.EmailVerificationEntity
import com.commerce.domain.common.auth.model.UserEntity
import org.jetbrains.exposed.sql.ResultRow

/**
 * Repository interface for authentication-related database operations.
 * Defines the contract for user persistence and retrieval.
 */
interface AuthRepository {

    /**
     * Finds a user in the database by their username or email.
     * Used primarily for login validation.
     *
     * @param usernameOrEmail The username or email to search.
     * @return A [ResultRow] containing user data if found, or null if not.
     */
    suspend fun findUser(usernameOrEmail: String): ResultRow?

    /**
     * Retrieves the email verification record associated with a user.
     *
     * @param userId The ID of the user.
     * @return The corresponding [EmailVerificationEntity] if exists, else null.
     */
    suspend fun getEmailVerification(userId: String): EmailVerificationEntity?

    /**
     * Inserts a new user into the database.
     *
     * @param userEntity The user details to persist.
     * @return The generated unique user ID after insertion.
     */
    suspend fun insertUser(userEntity: UserEntity): String

    /**
     * Inserts or updates an email verification record.
     * Ensures only one verification record exists per user (upsert behavior).
     *
     * @param entity The verification data to insert or update.
     * @return The userId associated with the verification record.
     */
    suspend fun upsertEmailVerification(entity: EmailVerificationEntity): String

    /**
     * Marks a user's email as verified by updating the corresponding flag.
     *
     * @param userId The ID of the user whose email is to be verified.
     * @return True if the update was successful, false otherwise.
     */
    suspend fun markEmailAsVerified(userId: String): Boolean
}
