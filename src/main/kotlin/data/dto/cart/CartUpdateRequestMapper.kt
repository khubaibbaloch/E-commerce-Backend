package com.commerce.data.dto.cart

import com.commerce.domain.models.cart.CartUpdateEntity

fun CartUpdateRequest.toDomain(): CartUpdateEntity {
    return CartUpdateEntity(quantity = this.quantity)
}