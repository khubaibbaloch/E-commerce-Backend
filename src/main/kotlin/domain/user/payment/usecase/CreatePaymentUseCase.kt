package domain.user.payment.usecase

import com.commerce.domain.user.payment.model.PaymentRequestEntity
import com.commerce.domain.user.payment.service.PaymentService

/**
 * Use case class responsible for handling the creation of payments.
 *
 * @property paymentService The payment service used to perform payment operations.
 */
class CreatePaymentUseCase(
    private val paymentService: PaymentService
) {
    /**
     * Executes the use case to create a payment for the specified user.
     *
     * @param request The payment request data containing order details and payment info.
     * @param userId The ID of the user making the payment.
     * @return The unique ID of the newly created payment.
     */
    suspend operator fun invoke(request: PaymentRequestEntity, userId: String): String {
        return paymentService.createPayment(request, userId)
    }
}
