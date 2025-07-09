package domain.user.payment.usecase

data class PaymentUseCase(
    val createPayment: CreatePaymentUseCase,
    val getPayments: GetPaymentsUseCase
)
