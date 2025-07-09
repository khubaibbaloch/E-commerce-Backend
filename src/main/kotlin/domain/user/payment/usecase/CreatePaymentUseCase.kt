package domain.user.payment.usecase

import com.commerce.domain.payment.model.PaymentRequestEntity
import com.commerce.domain.payment.service.PaymentService

class CreatePaymentUseCase(
    private val paymentService: PaymentService
) {
    suspend operator fun invoke(request: PaymentRequestEntity, userId: String): String {
        return paymentService.createPayment(request, userId)
    }
}
