package com.commerce.domain.user.product.service

import com.commerce.domain.user.product.model.ProductEntityWithId

/**
 * Service interface responsible for handling business logic related to user-accessible products.
 */
interface UserProductService {

    /**
     * Searches for products by their name.
     *
     * @param productName The name of the product to search.
     * @return A list of products matching the given name.
     */
    suspend fun findProductByName(productName: String): List<ProductEntityWithId>

    /**
     * Retrieves all available products for users.
     *
     * @return A list of all products.
     */
    suspend fun getAllProducts(): List<ProductEntityWithId>
}
