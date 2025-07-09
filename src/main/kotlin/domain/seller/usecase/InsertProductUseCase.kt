package domain.user.product.usecase

import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.service.SellerProductService
import com.commerce.domain.user.product.service.UserProductService

class InsertProductUseCase(
    private val sellerProductService: SellerProductService
) {
    suspend operator fun invoke(productEntity: ProductEntity): String {
        return sellerProductService.insertProduct(productEntity)
    }
}
