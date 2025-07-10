package com.commerce.domain.seller.service

import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate

/**
 * Service interface for handling business logic related to seller product operations.
 * This layer typically contains validations, authorization checks, and delegates data persistence to the repository.
 */
interface SellerProductService {

    /**
     * Updates the product identified by [productId] with new details provided in [updatedProduct].
     *
     * @param productId The unique identifier of the product to be updated.
     * @param updatedProduct Object containing updated product fields.
     * @return True if the update was successful, false otherwise.
     */
    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean

    /**
     * Deletes the product with the given [productId] from the seller's catalog.
     *
     * @param productId The unique identifier of the product to delete.
     * @return True if deletion was successful, false otherwise.
     */
    suspend fun deleteProductById(productId: String): Boolean

    /**
     * Inserts a new product into the seller's product catalog.
     *
     * @param productEntity The product information to be inserted.
     * @return The generated product ID as a string.
     */
    suspend fun insertProduct(productEntity: ProductEntity): String
}
