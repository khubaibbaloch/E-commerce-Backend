package com.commerce.domain.user.payment.repository

import com.commerce.domain.user.payment.model.PaymentEntity

/**
 * Repository interface for managing payment data.
 */
interface PaymentRepository {
    /**
     * Creates a new payment record.
     *
     * @param payment The payment entity to be created.
     * @return The unique ID of the created payment.
     */
    suspend fun createPayment(payment: PaymentEntity): String

    /**
     * Retrieves all payment records associated with a specific user.
     *
     * @param userId The ID of the user whose payments are being queried.
     * @return A list of payment entities for the user.
     */
    suspend fun getPaymentsByUser(userId: String): List<PaymentEntity>
}
