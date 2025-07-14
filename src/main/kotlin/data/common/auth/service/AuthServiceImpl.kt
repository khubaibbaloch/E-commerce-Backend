package com.commerce.data.common.auth.service

import com.commerce.data.common.auth.table.UsersTable
import com.commerce.domain.common.auth.model.EmailVerificationEntity
import com.commerce.domain.common.auth.model.UserEntity
import com.commerce.domain.common.auth.repository.AuthRepository
import com.commerce.domain.common.auth.service.AuthService
import domain.common.auth.model.RegisterResult

/**
 * Implementation of [AuthService] responsible for handling user authentication and email verification logic.
 *
 * Acts as a bridge between the domain layer and the data layer ([AuthRepository]).
 *
 * @property repository Data access implementation of authentication-related operations.
 */
class AuthServiceImpl(private val repository: AuthRepository) : AuthService {

    /**
     * Registers a new user if the email (username) is not already taken.
     *
     * @param userEntity Domain-level user data (email, password, etc.).
     * @return [RegisterResult.NewUser] with new user ID if registration is successful,
     *         or [RegisterResult.ExistingUser] if user already exists.
     */
    override suspend fun registerAndReturnUserId(userEntity: UserEntity): RegisterResult {
        val existing = repository.findUser(userEntity.email)
        return if (existing != null) {
            RegisterResult.ExistingUser(existing[UsersTable.userid])
        } else {
            val newId = repository.insertUser(userEntity)
            RegisterResult.NewUser(newId)
        }
    }

    /**
     * Stores or updates a user's email verification record (OTP or token).
     *
     * @param entity The email verification record to be saved.
     * @return The associated user ID.
     */
    override suspend fun upsertEmailVerification(entity: EmailVerificationEntity): String {
        return repository.upsertEmailVerification(entity)
    }

    /**
     * Logs a user in by verifying email/username and password.
     *
     * @param userEntity The user's credentials.
     * @return The user's UUID if valid; otherwise, null.
     */
    override suspend fun login(userEntity: UserEntity): String? {
        val user = repository.findUser(userEntity.username)

        // ⚠️ Note: Consider hashing passwords for security in production.
        return if (user != null && user[UsersTable.password] == userEntity.password) {
            user[UsersTable.userid]
        } else {
            null
        }
    }

    /**
     * Retrieves the email verification record for the specified user.
     *
     * @param userId The unique user ID.
     * @return The [EmailVerificationEntity] if found, else null.
     */
    override suspend fun getEmailVerification(userId: String): EmailVerificationEntity? {
        return repository.getEmailVerification(userId)
    }

    /**
     * Marks the email as verified for the given user ID.
     *
     * @param userId The unique user ID whose email is being confirmed.
     * @return True if update was successful; false otherwise.
     */
    override suspend fun markEmailAsVerified(userId: String): Boolean {
        return repository.markEmailAsVerified(userId)
    }
}
