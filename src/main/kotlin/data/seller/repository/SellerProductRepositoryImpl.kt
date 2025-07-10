package com.commerce.data.seller.repository

import com.commerce.data.seller.table.ProductTable
import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate
import com.commerce.domain.seller.repository.SellerProductRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

/**
 * Repository implementation for Seller-related product operations.
 * Communicates with the database using Exposed DSL.
 */
class SellerProductRepositoryImpl(private val database: Database): SellerProductRepository {

    init {
        transaction(database) {
            // Create ProductTable if it doesn't exist. Use with caution in prod.
            SchemaUtils.create(ProductTable)
        }
    }

    /**
     * Updates an existing product identified by [productId] with new values.
     * @return true if update was successful, false otherwise.
     */
    override suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean = dbQuery {
        val rowsUpdated = ProductTable.update({ ProductTable.productId eq productId }) {
            it[name] = updatedProduct.name
            it[description] = updatedProduct.description
            it[price] = updatedProduct.price
            it[quantity] = updatedProduct.quantity
        }
        rowsUpdated > 0
    }

    /**
     * Deletes a product by its [productId].
     * @return true if deletion was successful.
     */
    override suspend fun deleteProductById(productId: String): Boolean = dbQuery {
        val rowsDeleted = ProductTable.deleteWhere { ProductTable.productId eq productId }
        rowsDeleted > 0
    }

    /**
     * Inserts a new product into the ProductTable.
     * @return The auto-generated product ID (UUID).
     */
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

    /**
     * Executes a given suspending DB transaction block on IO dispatcher.
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
