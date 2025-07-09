package com.commerce.domain.cart.usecase

import domain.user.cart.usecase.DeleteCartUseCase
import domain.user.cart.usecase.UpdateCartUseCase

data class CartUseCase(
    val addCart: AddCartUseCase,
    val deleteCart: DeleteCartUseCase,
    val findCartByUserId: FindCartByUserIdUseCase,
    val updateCart: UpdateCartUseCase
)