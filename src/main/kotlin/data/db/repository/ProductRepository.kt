package com.commerce.data.db.repository

import com.commerce.data.db.tables.ProductTable
import com.commerce.data.db.tables.UsersTable
import domain.models.ProductEntity
import domain.models.UserEntity
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

    suspend fun findProductsByName(productName: String): List<ProductEntity> = dbQuery {
        ProductTable.select { ProductTable.name eq productName }
            .map {
                ProductEntity(
                    sellerId = it[ProductTable.sellerId],
                    name = it[ProductTable.name],
                    description = it[ProductTable.description],
                    price = it[ProductTable.price],
                    quantity = it[ProductTable.quantity]
                )
            }
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