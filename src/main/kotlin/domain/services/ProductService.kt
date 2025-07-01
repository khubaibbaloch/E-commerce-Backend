package com.commerce.domain.services

import com.commerce.data.db.repository.ProductRepository
import com.commerce.domain.models.product.ProductEntityWithId
import com.commerce.domain.models.product.ProductUpdate
import com.commerce.domain.models.product.ProductEntity

class ProductService(private val productRepository: ProductRepository) {
    suspend fun insertProduct(productEntity: ProductEntity): String {
        return productRepository.insertProduct(productEntity)
    }

    suspend fun findProductByName(productName: String): List<ProductEntityWithId> {
        return productRepository.findProductsByName(productName)
    }

    suspend fun getAllProducts(): List<ProductEntityWithId> {
        return productRepository.getAllProducts()
    }

    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean {
        return productRepository.updateProductById(productId = productId, updatedProduct = updatedProduct)
    }
    suspend fun deleteProductById(productId: String): Boolean {
        return productRepository.deleteProductById(productId = productId)
    }
}