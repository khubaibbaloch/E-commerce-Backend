package com.commerce.data.product.service

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.user.product.repository.UserProductRepository
import com.commerce.domain.user.product.service.UserProductService

class UserProductServiceImpl(private val userProductRepository: UserProductRepository):UserProductService {

    override suspend fun findProductByName(productName: String): List<ProductEntityWithId> {
        return userProductRepository.findProductsByName(productName)
    }

    override suspend fun getAllProducts(): List<ProductEntityWithId> {
        return userProductRepository.getAllProducts()
    }

}