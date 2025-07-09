package com.commerce.data.product.repository

import com.commerce.data.seller.table.ProductTable
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.user.product.repository.UserProductRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserProductRepositoryImpl(private val database: Database):UserProductRepository {

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

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}