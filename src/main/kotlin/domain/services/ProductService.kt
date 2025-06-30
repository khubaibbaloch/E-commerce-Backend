package com.commerce.domain.services

import com.commerce.data.db.repository.ProductRepository
import domain.models.ProductEntity

class ProductService(private val productRepository: ProductRepository) {
    suspend fun insertProduct(productEntity: ProductEntity):String{
        return productRepository.insertProduct(productEntity)
    }
    suspend fun findProductByName(productName:String):List<ProductEntity>{
        return productRepository.findProductsByName(productName)
    }
}