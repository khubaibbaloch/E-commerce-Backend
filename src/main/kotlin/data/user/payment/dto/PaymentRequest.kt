package com.commerce.data.user.payment.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a request to initiate or update a payment.
 */
@Serializable
data class PaymentRequest(

    /**
     * Unique identifier of the order for which payment is being made.
     */
    val orderId: String,

    /**
     * Payment method used by the user (e.g., "CreditCard", "PayPal", "CashOnDelivery").
     */
    val paymentMethod: String,

    /**
     * Current status of the payment (e.g., "PENDING", "COMPLETED", "FAILED").
     */
    val status: String
)
