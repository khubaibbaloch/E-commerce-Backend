package com.commerce.data.common.auth.repository

import com.commerce.data.common.auth.table.EmailVerificationsTable
import com.commerce.data.common.auth.table.UsersTable
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
            // Ensures UsersTable is created in the database (run only on app startup)
            // SchemaUtils.drop(UsersTable)  // Uncomment for debugging DB reset
            SchemaUtils.create(UsersTable, EmailVerificationsTable)
        }


    }

    /**
     * Inserts a new user into the database.
     * @param userEntity The domain-level user entity.
     * @return The generated unique user ID.
     */
    override suspend fun insertUser(userEntity: UserEntity): String = dbQuery {
        val generatedUUID = UUID.randomUUID().toString()
        UsersTable.insert {
            it[userid] = generatedUUID
            it[username] = userEntity.username
            it[email] = userEntity.email
            it[password] = userEntity.password // Store hashed password if hashing applied upstream
        }
        generatedUUID
    }

    /**
     * Finds a user by username.
     * Used primarily for login validation.
     * @param usernameOrEmail The username to search.
     * @return The corresponding ResultRow if found, else null.
     */
    override suspend fun findUser(usernameOrEmail: String): ResultRow? = dbQuery {
        UsersTable.select {
            (UsersTable.username eq usernameOrEmail) or
                    (UsersTable.email eq usernameOrEmail)
        }.singleOrNull()
    }


    /**
     * Helper function to execute DB queries on IO dispatcher in coroutine-safe way.
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
