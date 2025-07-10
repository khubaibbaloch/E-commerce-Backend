package domain.user.product.usecase

import com.commerce.domain.user.product.model.ProductEntityWithId
import com.commerce.domain.user.product.service.UserProductService

/**
 * Use case to retrieve all products available for users.
 *
 * This use case abstracts the logic of fetching all product listings
 * and delegates the operation to [UserProductService].
 */
class GetAllProductsUseCase(
    private val userProductService: UserProductService
) {
    /**
     * Executes the use case.
     *
     * @return A list of all [ProductEntityWithId] representing all products in the system.
     */
    suspend operator fun invoke(): List<ProductEntityWithId> {
        return userProductService.getAllProducts()
    }
}
