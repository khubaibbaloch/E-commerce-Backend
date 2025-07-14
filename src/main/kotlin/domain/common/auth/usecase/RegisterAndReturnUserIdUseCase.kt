package com.commerce.domain.common.auth.usecase

import com.commerce.domain.common.auth.model.UserEntity
import com.commerce.domain.common.auth.service.AuthService
import domain.common.auth.model.RegisterResult

/**
 * Use case for registering a new user and returning the result.
 *
 * Delegates registration logic to the [AuthService], allowing the domain layer
 * to remain decoupled from lower-level service or repository implementations.
 * This separation supports clean architecture and unit testing.
 *
 * @property authService The service responsible for handling user registration.
 */
class RegisterAndReturnUserIdUseCase(
    private val authService: AuthService
) {
    /**
     * Executes the user registration flow.
     *
     * @param user The user entity containing registration details.
     * @return A [RegisterResult] indicating whether the user is new or already exists.
     */
    suspend operator fun invoke(user: UserEntity): RegisterResult {
        return authService.registerAndReturnUserId(user)
    }
}
