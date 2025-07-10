package domain.user.product.usecase

/**
 * Aggregates all product-related use cases available to the user.
 *
 * This class serves as a container for grouping related use cases
 * to improve modularity and simplify dependency injection.
 *
 * @property findByName Use case to find products by their name.
 * @property getAll Use case to retrieve all available products.
 */
data class UserProductUseCase(
    val findByName: FindProductByNameUseCase,
    val getAll: GetAllProductsUseCase,
)
