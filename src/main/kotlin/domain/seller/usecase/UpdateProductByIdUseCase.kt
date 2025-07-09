package com.commerce.domain.seller.usecase

import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.service.SellerProductService

class UpdateProductByIdUseCase(
    private val sellerProductService: SellerProductService
) {
    suspend operator fun invoke(productId: String, update: ProductUpdate): Boolean {
        return sellerProductService.updateProductById(productId, update)
    }
}
