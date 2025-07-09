package com.commerce.domain.payment.service

import com.commerce.domain.payment.model.PaymentEntity
import com.commerce.domain.payment.model.PaymentRequestEntity

interface PaymentService {
    suspend fun createPayment(request: PaymentRequestEntity, userId: String): String
    suspend fun getPayments(userId: String): List<PaymentEntity>
}