package com.commerce.domain.payment.model

data class PaymentRequestEntity(
    val orderId: String,
    val paymentMethod: String,
    val status:String = "PENDING"
)