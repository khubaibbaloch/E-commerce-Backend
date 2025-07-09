package com.commerce.data.payment.service

import com.commerce.data.order.repository.OrderRepositoryImpl
import com.commerce.domain.payment.model.PaymentRequestEntity
import com.commerce.data.payment.repository.PaymentRepositoryImpl
import com.commerce.domain.order.repository.OrderRepository
import com.commerce.domain.payment.model.PaymentEntity
import com.commerce.domain.payment.repository.PaymentRepository
import com.commerce.domain.payment.service.PaymentService
import java.util.*

class PaymentServiceImpl(
    private val paymentRepository: PaymentRepository,
    private val orderRepository: OrderRepository // Needed to get total price
): PaymentService {
    override suspend fun createPayment(request: PaymentRequestEntity, userId: String): String {
        val order = orderRepository.getOrdersByUser(userId)
            .find { it.orderId == request.orderId }
            ?: throw IllegalArgumentException("Order not found")

        val payment = PaymentEntity(
            paymentId = UUID.randomUUID().toString(),
            orderId = request.orderId,
            userId = userId,
            amount = order.totalPrice,
            status = request.status,
            paymentMethod = request.paymentMethod,
            createdAt = System.currentTimeMillis()
        )
        orderRepository.updateOrderStatus(orderId = order.orderId,request.status)
        return paymentRepository.createPayment(payment)
    }

    override suspend fun getPayments(userId: String): List<PaymentEntity> {
        return paymentRepository.getPaymentsByUser(userId)
    }
}
