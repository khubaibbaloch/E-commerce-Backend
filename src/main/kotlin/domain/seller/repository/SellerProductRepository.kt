package com.commerce.domain.seller.repository

import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate

/**
 * Repository interface for managing seller-specific product operations.
 */
interface SellerProductRepository {

    /**
     * Updates an existing product's details using its unique ID.
     *
     * @param productId The ID of the product to be updated.
     * @param updatedProduct The updated product data.
     * @return True if the update was successful, false otherwise.
     */
    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean

    /**
     * Deletes a product from the catalog using its unique ID.
     *
     * @param productId The ID of the product to be deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    suspend fun deleteProductById(productId: String): Boolean

    /**
     * Inserts a new product into the catalog.
     *
     * @param productEntity The product entity containing all necessary product details.
     * @return The ID of the newly inserted product.
     */
    suspend fun insertProduct(productEntity: ProductEntity): String
}
