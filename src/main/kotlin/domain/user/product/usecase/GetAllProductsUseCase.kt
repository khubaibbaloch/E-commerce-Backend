package domain.user.product.usecase

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.user.product.service.UserProductService

class GetAllProductsUseCase(
    private val userProductService: UserProductService
) {
    suspend operator fun invoke(): List<ProductEntityWithId> {
        return userProductService.getAllProducts()
    }
}
