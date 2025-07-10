package com.commerce.domain.user.payment.model

/**
 * Represents the data needed to create a new payment request.
 *
 * @property orderId The ID of the order for which the payment is made.
 * @property paymentMethod The method of payment chosen by the user (e.g., CREDIT_CARD, PAYPAL).
 * @property status The current status of the payment request, defaults to "PENDING".
 */
data class PaymentRequestEntity(
    val orderId: String,
    val paymentMethod: String,
    val status: String = "PENDING"
)
