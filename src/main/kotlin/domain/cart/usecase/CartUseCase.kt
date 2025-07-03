package com.commerce.domain.cart.usecase

import domain.cart.usecase.DeleteCartUseCase
import domain.cart.usecase.UpdateCartUseCase

data class CartUseCase(
    val addCart: AddCartUseCase,
    val deleteCart: DeleteCartUseCase,
    val findCartByUserId: FindCartByUserIdUseCase,
    val updateCart: UpdateCartUseCase
)