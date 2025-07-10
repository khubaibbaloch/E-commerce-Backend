package com.commerce.domain.seller.usecase

import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.service.SellerProductService

/**
 * Use case for inserting a new product.
 * Delegates the actual product creation logic to the SellerProductService.
 *
 * @property sellerProductService The service layer handling product-related operations.
 */
class InsertProductUseCase(
    private val sellerProductService: SellerProductService
) {
    /**
     * Executes the use case to insert a new product into the system.
     *
     * @param productEntity The product details to be added.
     * @return The unique identifier of the newly created product.
     */
    suspend operator fun invoke(productEntity: ProductEntity): String {
        return sellerProductService.insertProduct(productEntity)
    }
}
