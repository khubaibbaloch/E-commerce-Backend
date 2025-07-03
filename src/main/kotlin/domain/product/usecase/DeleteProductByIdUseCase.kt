package domain.product.usecase

import com.commerce.domain.product.service.ProductService

class DeleteProductByIdUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(productId: String): Boolean {
        return productService.deleteProductById(productId)
    }
}
