package com.commerce.data.seller.repository

import com.commerce.data.seller.table.ProductTable
import com.commerce.data.seller.table.ProductTable.description
import com.commerce.data.seller.table.ProductTable.name
import com.commerce.data.seller.table.ProductTable.price
import com.commerce.data.seller.table.ProductTable.productId
import com.commerce.data.seller.table.ProductTable.quantity
import com.commerce.data.seller.table.ProductTable.sellerId
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.repository.SellerProductRepository
import com.commerce.domain.user.product.repository.UserProductRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class SellerProductRepositoryImpl(private val database: Database): SellerProductRepository {

    init {
        transaction(database) {
            //SchemaUtils.drop(ProductTable)
            SchemaUtils.create(ProductTable)
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