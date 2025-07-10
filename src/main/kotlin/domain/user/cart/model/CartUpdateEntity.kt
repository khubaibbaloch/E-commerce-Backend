package com.commerce.domain.user.cart.model

/**
 * Domain model used to represent an update operation for a cart item.
 *
 * @property quantity The new quantity to be updated for the cart item.
 */
class CartUpdateEntity(
    val quantity: Int,
)
