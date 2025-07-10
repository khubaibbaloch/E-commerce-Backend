package com.commerce.data.user.product.service

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.user.product.repository.UserProductRepository
import com.commerce.domain.user.product.service.UserProductService

/**
 * Implementation of [UserProductService] responsible for providing
 * product-related functionalities to end-users (read-only operations).
 *
 * This service acts as an intermediary layer between the controller/presentation
 * and the data access (repository) layer, enabling separation of concerns.
 */
class UserProductServiceImpl(
    private val userProductRepository: UserProductRepository
) : UserProductService {

    /**
     * Retrieves a list of products that exactly match the given name.
     *
     * @param productName The product name to search for.
     * @return A list of matching [ProductEntityWithId] items.
     */
    override suspend fun findProductByName(productName: String): List<ProductEntityWithId> {
        return userProductRepository.findProductsByName(productName)
    }

    /**
     * Fetches all products available to the user.
     *
     * @return A list of all [ProductEntityWithId] items.
     */
    override suspend fun getAllProducts(): List<ProductEntityWithId> {
        return userProductRepository.getAllProducts()
    }

}
