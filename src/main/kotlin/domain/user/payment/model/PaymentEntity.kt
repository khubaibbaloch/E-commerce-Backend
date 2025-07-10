package com.commerce.domain.user.payment.model

/**
 * Represents a payment record associated with an order.
 *
 * @property paymentId Unique identifier for the payment.
 * @property orderId Identifier of the order this payment is linked to.
 * @property userId Identifier of the user who made the payment.
 * @property amount Amount paid.
 * @property status Current status of the payment (e.g., PENDING, SUCCESS, FAILED).
 * @property paymentMethod Method used for payment (e.g., CREDIT_CARD, PAYPAL).
 * @property createdAt Timestamp (in milliseconds) when the payment was created.
 */
data class PaymentEntity(
    val paymentId: String,
    val orderId: String,
    val userId: String,
    val amount: Double,
    val status: String,
    val paymentMethod: String,
    val createdAt: Long
)
