package com.commerce.data.user.payment.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a response after a payment is processed.
 */
@Serializable
data class PaymentResponse(

    /**
     * Unique identifier of the payment transaction.
     */
    val paymentId: String,

    /**
     * ID of the order associated with this payment.
     */
    val orderId: String,

    /**
     * Total amount paid by the user for the order.
     */
    val amount: Double,

    /**
     * Status of the payment (e.g., "PENDING", "COMPLETED", "FAILED").
     */
    val status: String,

    /**
     * Payment method used (e.g., "CreditCard", "PayPal", "CashOnDelivery").
     */
    val paymentMethod: String,

    /**
     * Timestamp when the payment was created (usually in epoch milliseconds).
     */
    val createdAt: Long
)
