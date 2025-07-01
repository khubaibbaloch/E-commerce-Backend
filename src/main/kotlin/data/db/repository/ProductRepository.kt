package com.commerce.data.db.repository

import com.commerce.data.db.tables.product.ProductTable
import com.commerce.domain.models.product.ProductEntityWithId
import com.commerce.domain.models.product.ProductUpdate
import com.commerce.domain.models.product.ProductEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ProductRepository(private val database: Database) {

    init {
        transaction(database) {
            //SchemaUtils.drop(ProductTable)
            SchemaUtils.create(ProductTable)
        }
    }

    suspend fun findProductsByName(productName: String): List<ProductEntityWithId> = dbQuery {
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

    suspend fun getAllProducts(): List<ProductEntityWithId> = dbQuery {
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
    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean = dbQuery {
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
    suspend fun deleteProductById(productId: String): Boolean = dbQuery {
        val rowsDeleted = ProductTable.deleteWhere { ProductTable.productId eq productId }
        rowsDeleted > 0 // true if delete happened
    }


    suspend fun insertProduct(productEntity: ProductEntity): String = dbQuery {
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