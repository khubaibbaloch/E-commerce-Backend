package data.dto.payment
import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponse(
    val paymentId: String,
    val orderId: String,
    val amount: Double,
    val status: String,
    val paymentMethod: String,
    val createdAt: Long
)