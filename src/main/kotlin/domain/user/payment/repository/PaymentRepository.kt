package com.commerce.domain.payment.repository

import com.commerce.domain.payment.model.PaymentEntity

interface PaymentRepository {
    suspend fun createPayment(payment: PaymentEntity): String
    suspend fun getPaymentsByUser(userId: String): List<PaymentEntity>
}