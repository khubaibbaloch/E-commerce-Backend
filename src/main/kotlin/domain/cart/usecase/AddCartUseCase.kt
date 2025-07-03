package com.commerce.domain.cart.usecase

import com.commerce.domain.cart.model.CartEntity
import com.commerce.domain.cart.model.CartUpdateEntity
import com.commerce.domain.cart.model.CartWithProductInfo
import com.commerce.domain.cart.service.CartService

class AddCartUseCase(private val cartService: CartService) {
    suspend operator fun invoke (cartEntity: CartEntity):String{
        return cartService.addCart(cartEntity)
    }
}

