package com.commerce.domain.admin.usecase

import domain.user.product.usecase.GetAllProductsUseCase

/**
 * Aggregates all admin-related use cases into a single container.
 * This structure enables clean injection into controllers or route handlers.
 *
 * @property getAllUser Use case to fetch all registered users (admin-level access).
 * @property getAllProducts Use case to fetch all products (admin-level view).
 */
data class AdminUseCase(
    val getAllUser: GetAllUserUseCase,

    // Reusing existing user product use case for admin product access
    val getAllProducts: GetAllProductsUseCase
)
