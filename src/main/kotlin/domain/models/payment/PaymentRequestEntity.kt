package com.commerce.domain.models.payment

data class PaymentRequestEntity(
    val orderId: String,
    val paymentMethod: String,
    val status:String = "PENDING"
)