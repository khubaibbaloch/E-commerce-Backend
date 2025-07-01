package domain.models.payment

data class PaymentEntity(
    val paymentId: String,
    val orderId: String,
    val userId: String,
    val amount: Double,
    val status: String,
    val paymentMethod: String,
    val createdAt: Long
)
