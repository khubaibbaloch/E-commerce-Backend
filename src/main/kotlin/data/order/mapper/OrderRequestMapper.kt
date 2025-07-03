package com.commerce.data.order.mapper

import com.commerce.data.order.dto.OrderRequest
import com.commerce.domain.order.model.OrderEntity
import com.commerce.domain.order.model.OrderItemEntity

fun OrderRequest.toEntity(userId: String): OrderEntity {
    val items = this.items.map {
        OrderItemEntity(
            productId = it.productId,
            quantity = it.quantity,
            price = it.price
        )
    }
    val total = items.sumOf { it.price * it.quantity }
    return OrderEntity(
        orderId = "", // Will be generated
        userId = userId,
        totalPrice = total,
        status = "PENDING",
        createdAt = System.currentTimeMillis(),
        items = items
    )
}
