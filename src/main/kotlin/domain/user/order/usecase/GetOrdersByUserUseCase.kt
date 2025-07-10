package domain.user.order.usecase

import com.commerce.domain.user.order.model.OrderEntity
import com.commerce.domain.user.order.service.OrderService

/**
 * Use case for fetching all orders placed by a specific user.
 *
 * @property orderService The service responsible for handling order operations.
 */
class GetOrdersByUserUseCase(private val orderService: OrderService) {

    /**
     * Invokes the use case to retrieve all orders for the given user ID.
     *
     * @param userId The ID of the user whose orders are being requested.
     * @return A list of [OrderEntity] containing the user's orders.
     */
    suspend operator fun invoke(userId: String): List<OrderEntity> {
        return orderService.getOrdersByUser(userId)
    }
}
