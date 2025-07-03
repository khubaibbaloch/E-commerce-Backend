package domain.product.usecase

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.product.service.ProductService

class FindProductByNameUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(productName: String): List<ProductEntityWithId> {
        return productService.findProductByName(productName)
    }
}
