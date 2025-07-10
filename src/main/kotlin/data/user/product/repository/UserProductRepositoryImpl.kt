package com.commerce.data.user.product.repository

import com.commerce.data.seller.table.ProductTable
import com.commerce.domain.user.product.model.ProductEntityWithId
import com.commerce.domain.user.product.repository.UserProductRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Implementation of [UserProductRepository] for accessing product data
 * available to the end-user (read-only access).
 */
class UserProductRepositoryImpl(private val database: Database) : UserProductRepository {

    init {
        transaction(database) {
            // Ensure the product table schema is created if not already present.
            // Uncomment 'drop' during development only (e.g., schema reset).
            // SchemaUtils.drop(ProductTable)
            SchemaUtils.create(ProductTable)
        }
    }

    /**
     * Finds all products by their exact name.
     *
     * @param productName the name to search for
     * @return list of products with matching name
     */
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

    /**
     * Retrieves all products from the database.
     *
     * @return list of all available products
     */
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

    /**
     * Executes a suspended transaction on the IO dispatcher to perform database queries safely.
     *
     * @param block the suspending database logic to execute
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
