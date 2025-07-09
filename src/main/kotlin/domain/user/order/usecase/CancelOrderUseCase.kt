package domain.user.order.usecase

import com.commerce.domain.order.service.OrderService

class CancelOrderUseCase(private val orderService: OrderService) {
    suspend operator fun invoke(orderId: String, userId: String): Boolean{
        return orderService.cancelOrder(orderId, userId)
    }
}
