package com.commerce.domain.seller.usecase

import domain.user.product.usecase.DeleteProductByIdUseCase
import domain.user.product.usecase.InsertProductUseCase

data class SellerProductUseCase(
    val insert: InsertProductUseCase,
    val updateById: UpdateProductByIdUseCase,
    val deleteById: DeleteProductByIdUseCase
)
