package com.commerce.domain.user.cart.usecase

import domain.user.cart.usecase.DeleteCartUseCase
import domain.user.cart.usecase.UpdateCartUseCase

/**
 * Aggregates all cart-related use cases into a single class.
 * This allows easy injection of a unified use case object into presentation layers (e.g., routing).
 *
 * @property addCart Use case to add an item to the user's cart.
 * @property deleteCart Use case to delete an item from the user's cart.
 * @property findCartByUserId Use case to retrieve all cart items for a given user.
 * @property updateCart Use case to update the quantity of a cart item.
 */
data class CartUseCase(
    val addCart: AddCartUseCase,
    val deleteCart: DeleteCartUseCase,
    val findCartByUserId: FindCartByUserIdUseCase,
    val updateCart: UpdateCartUseCase
)
