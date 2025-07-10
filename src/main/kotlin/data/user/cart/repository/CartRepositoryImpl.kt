package com.commerce.data.user.cart.repository

import com.commerce.data.user.cart.table.CartTable
import com.commerce.data.seller.table.ProductTable
import com.commerce.domain.user.cart.model.CartEntity
import com.commerce.domain.user.cart.model.CartUpdateEntity
import com.commerce.domain.user.product.model.ProductEntityWithId
import com.commerce.domain.user.cart.model.CartWithProductInfo
import com.commerce.domain.user.cart.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Repository implementation for managing cart operations in the database.
 * Handles CRUD operations for the user's cart and also joins with products.
 */
class CartRepositoryImpl(private val database: Database): CartRepository {

    init {
        // Initializes the CartTable schema when this repository is first constructed.
        // Uncomment SchemaUtils.drop(...) during development to reset the table.
        transaction(database) {
            //SchemaUtils.drop(CartTable)
            SchemaUtils.create(CartTable)
        }
    }

    /**
     * Adds a product to the user's cart.
     * Generates a unique UUID for the cart entry.
     */
    override suspend fun addCart(cartEntity: CartEntity): String = dbQuery {
        val generatedUUID = UUID.randomUUID().toString()
        CartTable.insert {
            it[cartId] = generatedUUID
            it[userId] = cartEntity.userId
            it[productId] = cartEntity.productId
            it[quantity] = cartEntity.quantity
        }
        return@dbQuery generatedUUID
    }

    /**
     * Returns a list of all items in the user's cart along with full product details.
     * Performs a SQL JOIN between CartTable and ProductTable.
     */
    override suspend fun findCartByUserId(userId: String): List<CartWithProductInfo> = dbQuery {
        CartTable.join(
            ProductTable,
            JoinType.INNER,
            onColumn = CartTable.productId,
            otherColumn = ProductTable.productId
        )
            .select { CartTable.userId eq userId }
            .map {
                CartWithProductInfo(
                    cartId = it[CartTable.cartId],
                    userId = it[CartTable.userId],
                    quantity = it[CartTable.quantity],
                    product = ProductEntityWithId(
                        productId = it[ProductTable.productId],
                        sellerId = it[ProductTable.sellerId],
                        name = it[ProductTable.name],
                        description = it[ProductTable.description],
                        price = it[ProductTable.price],
                        quantity = it[ProductTable.quantity]
                    )
                )
            }
    }

    /**
     * Updates the quantity of a specific item in the cart by cart ID.
     * Returns true if at least one row was updated.
     */
    override suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean = dbQuery {
        val rowsUpdated = CartTable.update({ CartTable.cartId eq cartId }) {
            it[quantity] = updateEntity.quantity
        }
        return@dbQuery rowsUpdated > 0
    }

    /**
     * Deletes a cart item by its cart ID.
     * Returns true if the deletion was successful.
     */
    override suspend fun deleteCart(cartId: String): Boolean = dbQuery {
        val rowsDeleted = CartTable.deleteWhere { CartTable.cartId eq cartId }
        return@dbQuery rowsDeleted > 0
    }

    /**
     * Utility function to run all DB operations in the IO coroutine dispatcher.
     * Ensures non-blocking DB access for better performance.
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
