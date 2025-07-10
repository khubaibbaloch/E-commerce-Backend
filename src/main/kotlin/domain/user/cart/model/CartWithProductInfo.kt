package com.commerce.domain.user.cart.model

import com.commerce.domain.user.product.model.ProductEntityWithId

/**
 * Domain model that represents a cart item along with the product details.
 *
 * This is useful when displaying cart items in the frontend with all relevant product information.
 *
 * @property cartId Unique identifier for the cart item.
 * @property userId ID of the user who owns the cart item.
 * @property quantity Number of units of the product added to the cart.
 * @property product Detailed product information associated with the cart item.
 */
data class CartWithProductInfo(
    val cartId: String,
    val userId: String,
    val quantity: Int,
    val product: ProductEntityWithId
)
