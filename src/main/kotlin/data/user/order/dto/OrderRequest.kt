package com.commerce.data.user.order.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing an order request from the user.
 * Contains a list of items the user wants to purchase in a single order.
 */
@Serializable
data class OrderRequest(
    // List of individual order items (products + quantity + price)
    val items: List<OrderItemRequest>
)
