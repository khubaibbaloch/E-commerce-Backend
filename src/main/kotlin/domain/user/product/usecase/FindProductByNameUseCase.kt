package domain.user.product.usecase

import com.commerce.domain.user.product.model.ProductEntityWithId
import com.commerce.domain.user.product.service.UserProductService

/**
 * Use case for finding products by their name for the user side.
 *
 * This encapsulates the logic of searching products based on a given product name
 * and delegates the actual logic to the [UserProductService].
 */
class FindProductByNameUseCase(
    private val userProductService: UserProductService
) {
    /**
     * Invokes the use case with the specified product name.
     *
     * @param productName The name of the product to search for.
     * @return A list of matching [ProductEntityWithId] objects.
     */
    suspend operator fun invoke(productName: String): List<ProductEntityWithId> {
        return userProductService.findProductByName(productName)
    }
}
