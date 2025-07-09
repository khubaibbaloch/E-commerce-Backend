package domain.user.product.usecase

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.user.product.service.UserProductService

class FindProductByNameUseCase(
    private val userProductService: UserProductService
) {
    suspend operator fun invoke(productName: String): List<ProductEntityWithId> {
        return userProductService.findProductByName(productName)
    }
}
