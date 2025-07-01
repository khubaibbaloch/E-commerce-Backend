package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    val orderId: String,
    val paymentMethod: String,
    val status:String
)