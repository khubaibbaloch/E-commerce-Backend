package domain.user.payment.usecase

import com.commerce.domain.user.payment.model.PaymentEntity
import com.commerce.domain.user.payment.service.PaymentService

/**
 * Use case class responsible for retrieving payments of a specific user.
 *
 * @property paymentService The payment service used to fetch payment data.
 */
class GetPaymentsUseCase(
    private val paymentService: PaymentService
) {
    /**
     * Executes the use case to get all payments made by the specified user.
     *
     * @param userId The ID of the user whose payments are to be retrieved.
     * @return A list of PaymentEntity representing the user's payments.
     */
    suspend operator fun invoke(userId: String): List<PaymentEntity> {
        return paymentService.getPayments(userId)
    }
}
