package com.commerce.domain.services

import com.commerce.data.db.repository.CartRepository
import com.commerce.domain.models.cart.CartEntity
import com.commerce.domain.models.cart.CartUpdateEntity
import com.commerce.domain.models.cart.CartWithProductInfo

class CartService(private val cartRepository: CartRepository) {
    suspend fun addCart(cartEntity: CartEntity): String{
        return cartRepository.addCart(cartEntity)
    }

    suspend fun findCartByUserId(userId: String): List<CartWithProductInfo>{
        return cartRepository.findCartByUserId(userId)
    }
    suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean{
        return cartRepository.updateCart(cartId,updateEntity)
    }
    suspend fun deleteCart(cartId: String): Boolean{
        return cartRepository.deleteCart(cartId)
    }
}