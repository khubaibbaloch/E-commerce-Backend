package domain.product.usecase

import com.commerce.domain.product.model.ProductEntity
import com.commerce.domain.product.service.ProductService

class InsertProductUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(productEntity: ProductEntity): String {
        return productService.insertProduct(productEntity)
    }
}
