package domain.services

import com.commerce.data.db.repository.OrderRepository
import com.commerce.domain.models.payment.PaymentRequestEntity
import data.db.repository.PaymentRepository
import data.dto.payment.PaymentRequest
import domain.models.payment.PaymentEntity
import java.util.*

class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val orderRepository: OrderRepository // Needed to get total price
) {
    suspend fun createPayment(request: PaymentRequestEntity, userId: String): String {
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

    suspend fun getPayments(userId: String): List<PaymentEntity> {
        return paymentRepository.getPaymentsByUser(userId)
    }
}
