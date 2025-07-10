package com.commerce.domain.common.auth.usecase

import com.commerce.domain.common.auth.model.UserEntity
import com.commerce.domain.common.auth.service.AuthService

/**
 * Use case for registering a new user and returning their user ID.
 *
 * This class delegates the user registration logic to the [AuthService],
 * ensuring that the domain layer remains decoupled from implementation details.
 * It enables clear separation of responsibilities and allows easy unit testing.
 *
 * @property authService Service that handles the actual user registration logic.
 */
class RegisterAndReturnUserIdUseCase(
    private val authService: AuthService
) {
    /**
     * Executes the user registration process.
     *
     * @param user The user data to be registered.
     * @return The unique user ID as a [String] if registration succeeds, or null otherwise.
     */
    suspend operator fun invoke(user: UserEntity): String? {
        return authService.registerAndReturnUserId(user)
    }
}
