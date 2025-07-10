package com.commerce.domain.user.product.repository

import com.commerce.domain.user.product.model.ProductEntityWithId

/**
 * Repository interface for accessing product data available to users.
 */
interface UserProductRepository {

    /**
     * Finds and returns a list of products that match the given product name.
     *
     * @param productName The name of the product to search for.
     * @return A list of products that match the provided name.
     */
    suspend fun findProductsByName(productName: String): List<ProductEntityWithId>

    /**
     * Retrieves all available products.
     *
     * @return A list containing all products in the system.
     */
    suspend fun getAllProducts(): List<ProductEntityWithId>
}
