package com.commerce.data.order.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val items: List<OrderItemRequest>
)