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
     * Finds a user in the database by their username.
     *
     * @param usernameOrEmail The username to search for.
     * @return A [ResultRow] containing user data if found, or null if not.
     */
    suspend fun findUser(usernameOrEmail: String): ResultRow?
    suspend fun getEmailVerification(userId: String): EmailVerificationEntity?
    /**
     * Inserts a new user into the database.
     *
     * @param userEntity The user details to persist.
     * @return The generated unique user ID after insertion.
     */
    suspend fun insertUser(userEntity: UserEntity): String
    suspend fun insertEmailVerification(entity: EmailVerificationEntity): String
}
