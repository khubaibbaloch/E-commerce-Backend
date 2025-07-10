package com.commerce.domain.common.auth.usecase

/**
 * Aggregates all authentication-related use cases.
 * This class helps group related functionality together and allows easy injection into the presentation layer.
 *
 * @property registerUseCase Use case for registering a new user and returning their ID.
 * @property loginUseCase Use case for authenticating a user and returning a JWT token.
 */
data class AuthUseCase(
    val registerUseCase: RegisterAndReturnUserIdUseCase,
    val loginUseCase: LoginUseCase
)
