package com.commerce.domain.auth.usecase

data class AuthUseCase(
    val registerUseCase: RegisterAndReturnUserIdUseCase,
    val loginUseCase: LoginUseCase
)
