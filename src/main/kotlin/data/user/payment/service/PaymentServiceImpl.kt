package com.commerce.data.user.payment.service

import com.commerce.domain.payment.model.PaymentRequestEntity
import com.commerce.domain.order.repository.OrderRepository
import com.commerce.domain.payment.model.PaymentEntity
import com.commerce.domain.payment.repository.PaymentRepository
import com.commerce.domain.payment.service.PaymentService
import java.util.*

/**
 * Service implementation for handling payment-related operations.
 * Acts as a bridge between domain logic and repository layer.
 */
class PaymentServiceImpl(
    private val paymentRepository: PaymentRepository,  // Handles DB operations for payments
    private val orderRepository: OrderRepository       // Used to retrieve order and validate ownership
) : PaymentService {

    /**
     * Creates a new payment for a given order by a user.
     *
     * Steps:
     *  - Verifies that the order exists and belongs to the requesting user.
     *  - Constructs a new PaymentEntity with order info and request details.
     *  - Updates the order status to reflect the payment result.
     *  - Stores the payment record in the database.
     *
     * @param request Payment request containing orderId, status, method
     * @param userId  ID of the currently logged-in user
     * @return        Newly created payment ID
     */
    override suspend fun createPayment(request: PaymentRequestEntity, userId: String): String {
        // Validate order ownership
        val order = orderRepository.getOrdersByUser(userId)
            .find { it.orderId == request.orderId }
            ?: throw IllegalArgumentException("Order not found")

        // Construct payment entity from request and order
        val payment = PaymentEntity(
            paymentId = UUID.randomUUID().toString(),  // Generate unique payment ID
            orderId = request.orderId,
            userId = userId,
            amount = order.totalPrice,                // Amount comes from associated order
            status = request.status,
            paymentMethod = request.paymentMethod,
            createdAt = System.currentTimeMillis()    // Set current timestamp
        )

        // Update the status of the order (e.g., to "PAID" or "FAILED")
        orderRepository.updateOrderStatus(orderId = order.orderId, newStatus = request.status)

        // Save payment to repository and return generated ID
        return paymentRepository.createPayment(payment)
    }

    /**
     * Retrieves all payments made by a specific user.
     *
     * @param userId ID of the user whose payments to fetch
     * @return       List of PaymentEntity containing user's payments
     */
    override suspend fun getPayments(userId: String): List<PaymentEntity> {
        return paymentRepository.getPaymentsByUser(userId)
    }
}
