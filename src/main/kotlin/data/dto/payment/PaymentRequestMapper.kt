package com.commerce.data.dto.payment

import com.commerce.domain.models.payment.PaymentRequestEntity
import data.dto.payment.PaymentRequest

fun PaymentRequest.toDomain(): PaymentRequestEntity {
    return PaymentRequestEntity(
        orderId = this.orderId,
        paymentMethod = this.paymentMethod,
        status = this.status
    )
}