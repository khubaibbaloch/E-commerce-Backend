package com.commerce.data.cart.mapper

import com.commerce.data.cart.dto.CartUpdateRequest
import com.commerce.domain.cart.model.CartUpdateEntity

fun CartUpdateRequest.toDomain(): CartUpdateEntity {
    return CartUpdateEntity(quantity = this.quantity)
}