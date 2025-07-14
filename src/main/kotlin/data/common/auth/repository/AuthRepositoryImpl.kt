package com.commerce.data.common.auth.repository

import com.commerce.data.common.auth.table.EmailVerificationsTable
import com.commerce.data.common.auth.table.UsersTable
import com.commerce.domain.common.auth.model.EmailVerificationEntity
import com.commerce.domain.common.auth.model.UserEntity
import com.commerce.domain.common.auth.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementation of AuthRepository using Exposed ORM and PostgreSQL/SQL database.
 * Handles user-related data operations such as registration and login.
 */
class AuthRepositoryImpl(private val database: Database) : AuthRepository {

    init {
        transaction(database) {
            // ✅ Automatically creates the Users and EmailVerifications tables if they don’t exist
            // SchemaUtils.drop(EmailVerificationsTable) // Uncomment only for development DB reset
            SchemaUtils.create(UsersTable, EmailVerificationsTable)
        }
    }

    /**
     * Inserts a new user into the database with a generated UUID.
     * @param userEntity The domain-level user entity.
     * @return The generated unique user ID.
     */
    override suspend fun insertUser(userEntity: UserEntity): String = dbQuery {
        val generatedUUID = UUID.randomUUID().toString()
        UsersTable.insert {
            it[userid] = generatedUUID
            it[username] = userEntity.username
            it[email] = userEntity.email
            it[password] = userEntity.password // ⚠️ Assumes password is already hashed
        }
        generatedUUID
    }

    /**
     * Inserts or updates an email verification entry based on userId.
     * @param entity The verification domain model.
     * @return The userId after upsert operation.
     */
    override suspend fun upsertEmailVerification(entity: EmailVerificationEntity): String = dbQuery {
        EmailVerificationsTable.upsert(
            keys = arrayOf(EmailVerificationsTable.userId), // 🔑 Conflict on userId
            onUpdate = listOf(
                EmailVerificationsTable.tokenOrOtp to stringLiteral(entity.tokenOrOtp),
                EmailVerificationsTable.expiresAt to stringLiteral(entity.expiresAt),
                EmailVerificationsTable.verified to booleanLiteral(entity.verified),
                EmailVerificationsTable.email to stringLiteral(entity.email)
            )
        ) {
            it[userId] = entity.userId
            it[email] = entity.email
            it[tokenOrOtp] = entity.tokenOrOtp
            it[expiresAt] = entity.expiresAt
            it[verified] = entity.verified
        }
        entity.userId
    }

    /**
     * Retrieves the email verification entry for the given user.
     * @param userId The user's unique identifier.
     * @return EmailVerificationEntity or null if not found.
     */
    override suspend fun getEmailVerification(userId: String): EmailVerificationEntity? = dbQuery {
        EmailVerificationsTable
            .select { EmailVerificationsTable.userId eq userId }
            .map {
                EmailVerificationEntity(
                    userId = it[EmailVerificationsTable.userId],
                    email = it[EmailVerificationsTable.email],
                    tokenOrOtp = it[EmailVerificationsTable.tokenOrOtp],
                    expiresAt = it[EmailVerificationsTable.expiresAt],
                    verified = it[EmailVerificationsTable.verified]
                )
            }
            .singleOrNull()
    }

    /**
     * Marks a user's email verification as complete (verified = true).
     * @param userId The ID of the user.
     * @return True if update affected at least one row.
     */
    override suspend fun markEmailAsVerified(userId: String): Boolean = dbQuery {
        val updatedRows = EmailVerificationsTable.update({ EmailVerificationsTable.userId eq userId }) {
            it[verified] = true
        }
        updatedRows > 0 // ✅ true if update succeeded
    }

    /**
     * Finds a user by username or email. Used during login.
     * @param usernameOrEmail The input credential (username or email).
     * @return ResultRow if a match is found, else null.
     */
    override suspend fun findUser(usernameOrEmail: String): ResultRow? = dbQuery {
        UsersTable.select {
            (UsersTable.username eq usernameOrEmail) or
                    (UsersTable.email eq usernameOrEmail)
        }.singleOrNull()
    }

    /**
     * Executes a suspend DB query block on the IO dispatcher using Exposed transactions.
     * Ensures coroutine safety and non-blocking behavior.
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
