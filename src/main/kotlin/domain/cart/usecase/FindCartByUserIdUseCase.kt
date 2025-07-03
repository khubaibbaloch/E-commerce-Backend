package com.commerce.domain.cart.usecase

import com.commerce.domain.cart.model.CartWithProductInfo
import com.commerce.domain.cart.service.CartService

class FindCartByUserIdUseCase(private val cartService: CartService) {
    suspend operator fun invoke (userId: String): List<CartWithProductInfo>{
        return cartService.findCartByUserId(userId = userId)
    }
}