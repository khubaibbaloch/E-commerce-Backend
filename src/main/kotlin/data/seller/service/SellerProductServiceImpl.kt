package com.commerce.data.seller.service

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.repository.SellerProductRepository
import com.commerce.domain.seller.service.SellerProductService
import com.commerce.domain.user.product.repository.UserProductRepository
import com.commerce.domain.user.product.service.UserProductService

class SellerProductServiceImpl(private val sellerProductRepository: SellerProductRepository): SellerProductService {
    override suspend fun insertProduct(productEntity: ProductEntity): String {
        return sellerProductRepository.insertProduct(productEntity)
    }

    override suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean {
        return sellerProductRepository.updateProductById(productId = productId, updatedProduct = updatedProduct)
    }
    override suspend fun deleteProductById(productId: String): Boolean {
        return sellerProductRepository.deleteProductById(productId = productId)
    }
}