package domain.product.usecase

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.product.service.ProductService

class GetAllProductsUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(): List<ProductEntityWithId> {
        return productService.getAllProducts()
    }
}
