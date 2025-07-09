package com.commerce.data.cart.repository

import com.commerce.data.cart.table.CartTable
import com.commerce.data.seller.table.ProductTable
import com.commerce.domain.cart.model.CartEntity
import com.commerce.domain.cart.model.CartUpdateEntity
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.cart.model.CartWithProductInfo
import com.commerce.domain.cart.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CartRepositoryImpl(private val database: Database):CartRepository{
    init {
        transaction(database) {
            //SchemaUtils.drop(CartTable)
            SchemaUtils.create(CartTable)
        }
    }

    override suspend fun addCart(cartEntity: CartEntity): String = dbQuery {
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

    override suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean = dbQuery {
        val rowsUpdated = CartTable.update({ CartTable.cartId eq cartId }) {
            it[quantity] = updateEntity.quantity
        }
        rowsUpdated > 0
    }
    override suspend fun deleteCart(cartId: String): Boolean = dbQuery {
        val rowsUpdated = CartTable.deleteWhere { CartTable.cartId eq cartId  }
        rowsUpdated > 0
    }


    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}