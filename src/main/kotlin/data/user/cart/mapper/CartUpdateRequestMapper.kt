package com.commerce.data.user.cart.mapper

import com.commerce.data.user.cart.dto.CartUpdateRequest
import com.commerce.domain.cart.model.CartUpdateEntity

/**
 * Maps CartUpdateRequest DTO (from client) to the domain-level CartUpdateEntity.
 *
 * @return A domain entity used to update the quantity of an existing cart item.
 */
fun CartUpdateRequest.toDomain(): CartUpdateEntity {
    return CartUpdateEntity(
        quantity = this.quantity // Updated quantity from client request
    )
}
