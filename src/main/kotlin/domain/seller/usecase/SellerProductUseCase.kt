package com.commerce.domain.seller.usecase

/**
 * Aggregates all use cases related to seller product operations.
 * This structure is useful for grouping multiple related operations
 * and injecting them as a single dependency in higher layers (e.g., ViewModel or Controller).
 *
 * @property insert Handles the logic for inserting a new product.
 * @property updateById Handles the logic for updating an existing product by its ID.
 * @property deleteById Handles the logic for deleting a product by its ID.
 */
data class SellerProductUseCase(
    val insert: InsertProductUseCase,
    val updateById: UpdateProductByIdUseCase,
    val deleteById: DeleteProductByIdUseCase
)
