package com.commerce.domain.cart.repository

import com.commerce.domain.cart.model.CartEntity
import com.commerce.domain.cart.model.CartUpdateEntity
import com.commerce.domain.cart.model.CartWithProductInfo

interface CartRepository {
    suspend fun addCart(cartEntity: CartEntity): String
    suspend fun findCartByUserId(userId: String): List<CartWithProductInfo>
    suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean
    suspend fun deleteCart(cartId: String): Boolean
}