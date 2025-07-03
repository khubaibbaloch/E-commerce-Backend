package com.commerce.data.order.repository

import com.commerce.data.order.table.OrderItemsTable
import com.commerce.data.order.table.OrdersTable
import com.commerce.domain.order.model.OrderEntity
import com.commerce.domain.order.model.OrderItemEntity
import com.commerce.domain.order.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class OrderRepositoryImpl(private val database: Database):OrderRepository {
    init {
        transaction(database) {
            //SchemaUtils.drop(CartTable)
            SchemaUtils.create(OrdersTable)
            SchemaUtils.create(OrderItemsTable)
        }
    }
    override suspend fun placeOrder(order: OrderEntity): String = dbQuery {
        val orderId = UUID.randomUUID().toString()

        OrdersTable.insert {
            it[OrdersTable.orderId] = orderId
            it[userId] = order.userId
            it[totalPrice] = order.totalPrice
            it[status] = order.status
            it[createdAt] = order.createdAt
        }

        order.items.forEach { item ->
            OrderItemsTable.insert {
                it[OrderItemsTable.orderId] = orderId
                it[productId] = item.productId
                it[quantity] = item.quantity
                it[price] = item.price
            }
        }
        orderId
    }

    override suspend fun getOrdersByUser(userId: String): List<OrderEntity> = dbQuery {
        val orders = OrdersTable.select { OrdersTable.userId eq userId }.map { row ->
            val orderId = row[OrdersTable.orderId]
            val items = OrderItemsTable.select { OrderItemsTable.orderId eq orderId }.map {
                OrderItemEntity(
                    productId = it[OrderItemsTable.productId],
                    quantity = it[OrderItemsTable.quantity],
                    price = it[OrderItemsTable.price]
                )
            }
            OrderEntity(
                orderId = orderId,
                userId = row[OrdersTable.userId],
                totalPrice = row[OrdersTable.totalPrice],
                status = row[OrdersTable.status],
                createdAt = row[OrdersTable.createdAt],
                items = items
            )
        }
        orders
    }

    override suspend fun getOrderById(orderId: String): OrderEntity? = dbQuery {
        OrdersTable.select { OrdersTable.orderId eq orderId }
            .mapNotNull { row ->
                val items = OrderItemsTable.select { OrderItemsTable.orderId eq orderId }.map {
                    OrderItemEntity(
                        productId = it[OrderItemsTable.productId],
                        quantity = it[OrderItemsTable.quantity],
                        price = it[OrderItemsTable.price]
                    )
                }
                OrderEntity(
                    orderId = row[OrdersTable.orderId],
                    userId = row[OrdersTable.userId],
                    totalPrice = row[OrdersTable.totalPrice],
                    status = row[OrdersTable.status],
                    createdAt = row[OrdersTable.createdAt],
                    items = items
                )
            }.firstOrNull()
    }

    override suspend fun updateOrderStatus(orderId: String, newStatus: String): Unit = dbQuery {
        OrdersTable.update({ OrdersTable.orderId eq orderId }) {
            it[status] = newStatus
        } > 0
    }


    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}