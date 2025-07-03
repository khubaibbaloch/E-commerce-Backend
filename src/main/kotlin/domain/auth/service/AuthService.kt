package com.commerce.domain.auth.service

import com.commerce.domain.auth.model.UserEntity

interface AuthService {
    suspend fun registerAndReturnUserId(userEntity: UserEntity): String?
    suspend fun login(userEntity: UserEntity): String?
}