package com.commerce.data.cart.service

import com.commerce.data.cart.repository.CartRepositoryImpl
import com.commerce.domain.cart.model.CartEntity
import com.commerce.domain.cart.model.CartUpdateEntity
import com.commerce.domain.cart.model.CartWithProductInfo
import com.commerce.domain.cart.repository.CartRepository
import com.commerce.domain.cart.service.CartService

class CartServiceImpl(private val cartRepository: CartRepository):CartService {
    override suspend fun addCart(cartEntity: CartEntity): String{
        return cartRepository.addCart(cartEntity)
    }

    override suspend fun findCartByUserId(userId: String): List<CartWithProductInfo>{
        return cartRepository.findCartByUserId(userId)
    }
    override suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean{
        return cartRepository.updateCart(cartId,updateEntity)
    }
    override suspend fun deleteCart(cartId: String): Boolean{
        return cartRepository.deleteCart(cartId)
    }
}