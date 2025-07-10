package com.commerce.domain.seller.usecase

import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.service.SellerProductService

/**
 * Use case for updating an existing product by its ID.
 * Delegates the update logic to the SellerProductService.
 *
 * @property sellerProductService Service responsible for handling product updates.
 */
class UpdateProductByIdUseCase(
    private val sellerProductService: SellerProductService
) {
    /**
     * Executes the product update operation.
     *
     * @param productId The ID of the product to update.
     * @param update The updated product data.
     * @return True if the update was successful, false otherwise.
     */
    suspend operator fun invoke(productId: String, update: ProductUpdate): Boolean {
        return sellerProductService.updateProductById(productId, update)
    }
}
