package com.commerce.data.seller.service

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.repository.SellerProductRepository
import com.commerce.domain.seller.service.SellerProductService

/**
 * Implementation of SellerProductService.
 * Responsible for coordinating product-related operations from the seller's perspective.
 */
class SellerProductServiceImpl(
    private val sellerProductRepository: SellerProductRepository
) : SellerProductService {

    /**
     * Inserts a new product into the system for a seller.
     * @param productEntity the product details provided by the seller
     * @return the generated product ID
     */
    override suspend fun insertProduct(productEntity: ProductEntity): String {
        return sellerProductRepository.insertProduct(productEntity)
    }

    /**
     * Updates an existing product by its product ID.
     * @param productId the unique product identifier
     * @param updatedProduct the updated product details
     * @return true if update was successful, false otherwise
     */
    override suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean {
        return sellerProductRepository.updateProductById(
            productId = productId,
            updatedProduct = updatedProduct
        )
    }

    /**
     * Deletes a product by its product ID.
     * @param productId the unique product identifier
     * @return true if deletion was successful, false otherwise
     */
    override suspend fun deleteProductById(productId: String): Boolean {
        return sellerProductRepository.deleteProductById(productId = productId)
    }
}
