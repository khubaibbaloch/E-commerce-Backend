package com.commerce.data.dto.order

import data.dto.order.OrderRequest
import domain.models.order.OrderEntity
import domain.models.order.OrderItemEntity

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
