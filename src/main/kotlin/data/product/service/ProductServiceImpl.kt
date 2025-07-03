package com.commerce.data.product.service

import com.commerce.data.product.repository.ProductRepositoryImpl
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.product.model.ProductUpdate
import com.commerce.domain.product.model.ProductEntity
import com.commerce.domain.product.repository.ProductRepository
import com.commerce.domain.product.service.ProductService

class ProductServiceImpl(private val productRepository: ProductRepository):ProductService {
    override suspend fun insertProduct(productEntity: ProductEntity): String {
        return productRepository.insertProduct(productEntity)
    }

    override suspend fun findProductByName(productName: String): List<ProductEntityWithId> {
        return productRepository.findProductsByName(productName)
    }

    override suspend fun getAllProducts(): List<ProductEntityWithId> {
        return productRepository.getAllProducts()
    }

    override suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean {
        return productRepository.updateProductById(productId = productId, updatedProduct = updatedProduct)
    }
    override suspend fun deleteProductById(productId: String): Boolean {
        return productRepository.deleteProductById(productId = productId)
    }
}