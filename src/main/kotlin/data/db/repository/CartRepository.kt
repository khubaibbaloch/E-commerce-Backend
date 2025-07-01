package com.commerce.data.db.repository

import com.commerce.data.db.tables.cart.CartTable
import com.commerce.data.db.tables.product.ProductTable
import com.commerce.domain.models.cart.CartEntity
import com.commerce.domain.models.cart.CartUpdateEntity
import com.commerce.domain.models.product.ProductEntityWithId
import com.commerce.domain.models.cart.CartWithProductInfo
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CartRepository(private val database: Database) {
    init {
        transaction(database) {
            //SchemaUtils.drop(CartTable)
            SchemaUtils.create(CartTable)
        }
    }

    suspend fun addCart(cartEntity: CartEntity): String = dbQuery {
        val generatedUUID = UUID.randomUUID().toString()
        CartTable.insert {
            it[cartId] = generatedUUID
            it[userId] = cartEntity.userId
            it[productId] = cartEntity.productId
            it[quantity] = cartEntity.quantity
        }
        generatedUUID
    }

//    suspend fun findCartByUserId(userId: String): List<CartEntity> = dbQuery {
//        CartTable.select { CartTable.userId eq userId }
//            .map {
//                CartEntity(
//                    cartId = it[CartTable.cartId],
//                    userId = it[CartTable.userId],
//                    productId = it[CartTable.productId],
//                    quantity = it[CartTable.quantity]
//                )
//            }
//    }

    suspend fun findCartByUserId(userId: String): List<CartWithProductInfo> = dbQuery {
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

    suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean = dbQuery {
        val rowsUpdated = CartTable.update({ CartTable.cartId eq cartId }) {
            it[quantity] = updateEntity.quantity
        }
        rowsUpdated > 0
    }
    suspend fun deleteCart(cartId: String): Boolean = dbQuery {
        val rowsUpdated = CartTable.deleteWhere { CartTable.cartId eq cartId  }
        rowsUpdated > 0
    }


    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}