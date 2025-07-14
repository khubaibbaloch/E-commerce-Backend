package domain.common.auth.usecase

import com.commerce.domain.common.auth.service.AuthService

/**
 * Use case for marking a user's email as verified.
 *
 * Delegates the verification update to the [AuthService], ensuring
 * that domain logic remains decoupled from infrastructure details.
 *
 * @property authService Service responsible for performing email verification updates.
 */
class MarkEmailAsVerifiedUseCase(
    private val authService: AuthService
) {
    /**
     * Marks the email associated with the provided [userId] as verified.
     *
     * @param userId The unique identifier of the user whose email is to be marked as verified.
     * @return `true` if the update was successful, `false` otherwise.
     */
    suspend operator fun invoke(userId: String): Boolean {
        return authService.markEmailAsVerified(userId)
    }
}
