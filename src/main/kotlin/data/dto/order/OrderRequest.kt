package data.dto.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val items: List<OrderItemRequest>
)