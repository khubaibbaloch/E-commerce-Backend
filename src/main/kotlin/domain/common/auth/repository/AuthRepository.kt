package com.commerce.domain.auth.repository

import com.commerce.domain.admin.model.UserAdminEntity
import com.commerce.domain.auth.model.UserEntity
import org.jetbrains.exposed.sql.ResultRow

interface AuthRepository {
    suspend fun findUser(username: String): ResultRow?
    suspend fun insertUser(userEntity: UserEntity): String
}