package domain.product.usecase

import com.commerce.domain.product.model.ProductUpdate
import com.commerce.domain.product.service.ProductService

class UpdateProductByIdUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(productId: String, update: ProductUpdate): Boolean {
        return productService.updateProductById(productId, update)
    }
}
