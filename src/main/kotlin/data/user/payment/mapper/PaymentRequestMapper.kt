package com.commerce.data.user.payment.mapper

import com.commerce.domain.user.payment.model.PaymentRequestEntity
import com.commerce.data.user.payment.dto.PaymentRequest

/**
 * Extension function to convert PaymentRequest DTO to domain layer entity.
 * This is used to map incoming API data to internal business logic models.
 */
fun PaymentRequest.toDomain(): PaymentRequestEntity {
    return PaymentRequestEntity(
        orderId = this.orderId,                // ID of the order being paid for
        paymentMethod = this.paymentMethod,    // Chosen method of payment (e.g., "PayPal", "Card")
        status = this.status                   // Initial payment status (e.g., "PENDING")
    )
}
