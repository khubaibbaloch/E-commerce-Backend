package com.commerce.domain.auth.usecase

import com.commerce.domain.auth.model.UserEntity
import com.commerce.domain.auth.service.AuthService

class RegisterAndReturnUserIdUseCase(
    private val authService: AuthService
) {
    suspend operator fun invoke(user: UserEntity): String? {
        return authService.registerAndReturnUserId(user)
    }
}