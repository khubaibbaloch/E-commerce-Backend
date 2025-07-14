package com.commerce.domain.common.auth.usecase

import com.commerce.domain.common.auth.model.EmailVerificationEntity
import com.commerce.domain.common.auth.service.AuthService

/**
 * Use case for retrieving email verification details by user ID.
 *
 * Delegates the retrieval to the [AuthService], enabling domain-level access to verification
 * metadata while maintaining separation from data layer implementations.
 *
 * @property authService Service responsible for user authentication operations.
 */
class GetEmailVerificationUseCase(
    private val authService: AuthService
) {
    /**
     * Retrieves the email verification entity associated with a specific user ID.
     *
     * @param userId The unique identifier of the user.
     * @return An [EmailVerificationEntity] if found, or null if no entry exists.
     */
    suspend operator fun invoke(userId: String): EmailVerificationEntity? {
        return authService.getEmailVerification(userId)
    }
}
