package com.commerce.domain.user.payment.service

import com.commerce.domain.user.payment.model.PaymentEntity
import com.commerce.domain.user.payment.model.PaymentRequestEntity

/**
 * Service interface defining payment-related operations.
 */
interface PaymentService {

    /**
     * Creates a payment for the given payment request and user.
     *
     * @param request The payment request details.
     * @param userId The ID of the user making the payment.
     * @return The unique ID of the created payment.
     */
    suspend fun createPayment(request: PaymentRequestEntity, userId: String): String

    /**
     * Retrieves all payments made by a specific user.
     *
     * @param userId The ID of the user whose payments are to be retrieved.
     * @return A list of PaymentEntity objects representing the user's payments.
     */
    suspend fun getPayments(userId: String): List<PaymentEntity>
}
