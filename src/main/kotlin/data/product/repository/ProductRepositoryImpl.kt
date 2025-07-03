package com.commerce.data.product.repository

import com.commerce.data.product.table.ProductTable
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.product.model.ProductUpdate
import com.commerce.domain.product.model.ProductEntity
import com.commerce.domain.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ProductRepositoryImpl(private val database: Database):ProductRepository {

    init {
        transaction(database) {
            //SchemaUtils.drop(ProductTable)
            SchemaUtils.create(ProductTable)
        }
    }

    override suspend fun findProductsByName(productName: String): List<ProductEntityWithId> = dbQuery {
        ProductTable.select { ProductTable.name eq productName }
            .map {
                ProductEntityWithId(
                    productId = it[ProductTable.productId],
                    sellerId = it[ProductTable.sellerId],
                    name = it[ProductTable.name],
                    description = it[ProductTable.description],
                    price = it[ProductTable.price],
                    quantity = it[ProductTable.quantity]
                )
            }
    }

    override suspend fun getAllProducts(): List<ProductEntityWithId> = dbQuery {
        ProductTable.selectAll()
            .map {
                ProductEntityWithId(
                    productId = it[ProductTable.productId],
                    sellerId = it[ProductTable.sellerId],
                    name = it[ProductTable.name],
                    description = it[ProductTable.description],
                    price = it[ProductTable.price],
                    quantity = it[ProductTable.quantity]
                )
            }
    }
    override suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean = dbQuery {
        val rowsUpdated = ProductTable.update({ ProductTable.productId eq productId }) {
            it[name] = updatedProduct.name
            it[description] = updatedProduct.description
            it[price] = updatedProduct.price
            it[quantity] = updatedProduct.quantity
        }
        rowsUpdated > 0 // true if update happened
    }
//    suspend fun updateProductById(productId: String, updatedProduct: ProductEntity): Boolean = dbQuery {
//        ProductTable.update({ ProductTable.productId eq productId }) {
//            it[sellerId] = updatedProduct.sellerId
//            it[name] = updatedProduct.name
//            it[description] = updatedProduct.description
//            it[price] = updatedProduct.price
//            it[quantity] = updatedProduct.quantity
//        }
//        true
//    }
    override suspend fun deleteProductById(productId: String): Boolean = dbQuery {
        val rowsDeleted = ProductTable.deleteWhere { ProductTable.productId eq productId }
        rowsDeleted > 0 // true if delete happened
    }


    override suspend fun insertProduct(productEntity: ProductEntity): String = dbQuery {
        val generatedUUID = UUID.randomUUID().toString()
        ProductTable.insert {
            it[productId] = generatedUUID
            it[sellerId] = productEntity.sellerId
            it[name] = productEntity.name
            it[description] = productEntity.description
            it[price] = productEntity.price
            it[quantity] = productEntity.quantity
        }
        generatedUUID
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}