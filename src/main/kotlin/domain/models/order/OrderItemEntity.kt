package domain.models.order

data class OrderItemEntity(
    val productId: String,
    val quantity: Int,
    val price: Double
)
