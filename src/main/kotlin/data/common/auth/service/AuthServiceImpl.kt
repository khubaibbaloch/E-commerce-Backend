package com.commerce.data.common.auth.service

import com.commerce.data.common.auth.table.UsersTable
import com.commerce.domain.auth.model.UserEntity
import com.commerce.domain.auth.repository.AuthRepository
import com.commerce.domain.auth.service.AuthService

/**
 * Implementation of [AuthService] responsible for user authentication and registration logic.
 * Acts as a bridge between the domain layer and data access layer.
 */
class AuthServiceImpl(private val repository: AuthRepository) : AuthService {

    /**
     * Registers a new user if the username is not already taken.
     * @param userEntity Domain-level user data (username + password).
     * @return The newly created user's ID, or null if the username already exists.
     */
    override suspend fun registerAndReturnUserId(userEntity: UserEntity): String? {
        val existing = repository.findUser(userEntity.username)
        return if (existing != null) null else repository.insertUser(userEntity)
    }

    /**
     * Authenticates a user by comparing credentials.
     * @param userEntity The user’s login request (username + password).
     * @return The user’s UUID if credentials are valid; otherwise, null.
     */
    override suspend fun login(userEntity: UserEntity): String? {
        val user = repository.findUser(userEntity.username)

        // You may want to hash and compare passwords securely instead of plain equality
        return if (user != null && user[UsersTable.password] == userEntity.password) {
            user[UsersTable.userid]
        } else {
            null
        }
    }
}
