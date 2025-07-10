package domain.user.payment.usecase

/**
 * Aggregates payment-related use cases into a single class for easier dependency injection and usage.
 *
 * @property createPayment Use case for creating a new payment.
 * @property getPayments Use case for retrieving payments by user.
 */
data class PaymentUseCase(
    val createPayment: CreatePaymentUseCase,
    val getPayments: GetPaymentsUseCase
)
