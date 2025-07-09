package domain.user.order.usecase

import com.commerce.domain.order.model.OrderEntity
import com.commerce.domain.order.service.OrderService

class GetOrdersByUserUseCase(private val orderService: OrderService) {
    suspend operator fun invoke(userId: String): List<OrderEntity>{
        return orderService.getOrdersByUser(userId)
    }
}