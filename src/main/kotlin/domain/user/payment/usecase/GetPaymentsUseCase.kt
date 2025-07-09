package domain.user.payment.usecase

import com.commerce.domain.payment.model.PaymentEntity
import com.commerce.domain.payment.service.PaymentService

class GetPaymentsUseCase(
    private val paymentService: PaymentService
) {
    suspend operator fun invoke(userId: String): List<PaymentEntity> {
        return paymentService.getPayments(userId)
    }
}
