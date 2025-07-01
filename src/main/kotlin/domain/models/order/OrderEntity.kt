package domain.models.order

data class OrderEntity(
    val orderId: String,
    val userId: String,
    val totalPrice: Double,
    val status: String,
    val createdAt: Long,
    val items: List<OrderItemEntity>
)
