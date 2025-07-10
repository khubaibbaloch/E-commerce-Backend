package com.commerce.domain.common.auth.usecase

import com.commerce.domain.common.auth.model.UserEntity
import com.commerce.domain.common.auth.service.AuthService

/**
 * Use case for handling user login.
 *
 * This class encapsulates the logic for authenticating a user.
 * It delegates the actual authentication process to the [AuthService],
 * promoting separation of concerns and making the domain layer more maintainable and testable.
 *
 * @property authService The service responsible for authentication logic.
 */
class LoginUseCase(
    private val authService: AuthService
) {
    /**
     * Invokes the login process with the provided [UserEntity].
     *
     * @param user The user credentials for login.
     * @return A JWT token if login is successful, or null if authentication fails.
     */
    suspend operator fun invoke(user: UserEntity): String? {
        return authService.login(user)
    }
}
