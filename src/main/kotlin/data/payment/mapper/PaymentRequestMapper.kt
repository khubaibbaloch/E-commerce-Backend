package com.commerce.data.payment.mapper

import com.commerce.domain.payment.model.PaymentRequestEntity
import com.commerce.data.payment.dto.PaymentRequest

fun PaymentRequest.toDomain(): PaymentRequestEntity {
    return PaymentRequestEntity(
        orderId = this.orderId,
        paymentMethod = this.paymentMethod,
        status = this.status
    )
}