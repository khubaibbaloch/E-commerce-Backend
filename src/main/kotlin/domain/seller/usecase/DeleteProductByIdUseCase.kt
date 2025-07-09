package domain.user.product.usecase

import com.commerce.domain.seller.service.SellerProductService
import com.commerce.domain.user.product.service.UserProductService

class DeleteProductByIdUseCase(
    private val sellerProductService: SellerProductService
) {
    suspend operator fun invoke(productId: String): Boolean {
        return sellerProductService.deleteProductById(productId)
    }
}
