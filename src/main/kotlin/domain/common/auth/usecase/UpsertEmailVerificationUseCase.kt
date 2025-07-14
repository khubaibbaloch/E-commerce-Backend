package domain.common.auth.usecase

import com.commerce.domain.common.auth.model.EmailVerificationEntity
import com.commerce.domain.common.auth.service.AuthService

/**
 * Use case for inserting or updating email verification information.
 *
 * This class delegates the logic to the [AuthService], allowing the domain layer
 * to remain clean and separated from the underlying implementation.
 *
 * @property authService The service that handles verification persistence logic.
 */
class UpsertEmailVerificationUseCase(
    private val authService: AuthService
) {
    /**
     * Executes the upsert operation for email verification.
     *
     * @param entity The [EmailVerificationEntity] containing verification details.
     * @return The user ID as a [String] after insertion or update.
     */
    suspend operator fun invoke(entity: EmailVerificationEntity): String {
        return authService.upsertEmailVerification(entity)
    }
}
