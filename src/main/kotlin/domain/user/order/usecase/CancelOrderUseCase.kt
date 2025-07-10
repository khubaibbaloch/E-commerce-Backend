package domain.user.order.usecase

import com.commerce.domain.user.order.service.OrderService

/**
 * Use case responsible for canceling an order.
 * It checks if the order exists, belongs to the user, and is still in a cancellable state.
 *
 * @property orderService The service handling order-related business logic.
 */
class CancelOrderUseCase(private val orderService: OrderService) {

    /**
     * Invokes the use case to cancel a specific order for a user.
     *
     * @param orderId The ID of the order to be canceled.
     * @param userId The ID of the user requesting the cancellation.
     * @return `true` if the order was successfully canceled, `false` otherwise.
     */
    suspend operator fun invoke(orderId: String, userId: String): Boolean {
        return orderService.cancelOrder(orderId, userId)
    }
}
