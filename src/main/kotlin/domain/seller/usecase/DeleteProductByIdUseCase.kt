package com.commerce.domain.seller.usecase

import com.commerce.domain.seller.service.SellerProductService

/**
 * Use case for deleting a product by its ID.
 * This encapsulates the business logic to delete a product and delegates to the service layer.
 *
 * @property sellerProductService The service responsible for seller product operations.
 */
class DeleteProductByIdUseCase(
    private val sellerProductService: SellerProductService
) {
    /**
     * Executes the use case to delete a product.
     *
     * @param productId The unique identifier of the product to delete.
     * @return True if the product was successfully deleted, false otherwise.
     */
    suspend operator fun invoke(productId: String): Boolean {
        return sellerProductService.deleteProductById(productId)
    }
}
