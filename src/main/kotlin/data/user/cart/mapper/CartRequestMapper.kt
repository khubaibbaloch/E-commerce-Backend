package com.commerce.data.user.cart.mapper

import com.commerce.data.user.cart.dto.CartRequest
import com.commerce.domain.user.cart.model.CartEntity

/**
 * Maps the CartRequest DTO (received from client) to a domain-level CartEntity.
 *
 * @param userId The ID of the user adding the item to the cart.
 * @return A domain entity representing the cart item.
 */
fun CartRequest.toDomain(userId: String): CartEntity {
    return CartEntity(
        userId = userId,                  // Associate the cart with the specific user
        productId = this.productId,       // Product the user wants to add
        quantity = this.quantity          // Quantity of the product to add
    )
}
