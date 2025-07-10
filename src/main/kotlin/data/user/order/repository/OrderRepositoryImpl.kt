package com.commerce.data.user.order.repository

import com.commerce.data.user.order.table.OrderItemsTable
import com.commerce.data.user.order.table.OrdersTable
import com.commerce.domain.user.order.model.OrderEntity
import com.commerce.domain.user.order.model.OrderItemEntity
import com.commerce.domain.user.order.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementation of [OrderRepository] that manages order persistence using Exposed ORM.
 *
 * @property database Database instance to run transactions on.
 */
class OrderRepositoryImpl(private val database: Database) : OrderRepository {

    init {
        // Initialize database schema for orders and order items tables.
        transaction(database) {
            SchemaUtils.create(OrdersTable)
            SchemaUtils.create(OrderItemsTable)
        }
    }

    /**
     * Places a new order by inserting into OrdersTable and OrderItemsTable atomically.
     * Generates a unique orderId.
     *
     * @param order The order entity to persist.
     * @return The generated orderId.
     */
    override suspend fun placeOrder(order: OrderEntity): String = dbQuery {
        val orderId = UUID.randomUUID().toString()

        // Insert order data
        OrdersTable.insert {
            it[OrdersTable.orderId] = orderId
            it[userId] = order.userId
            it[totalPrice] = order.totalPrice
            it[status] = order.status
            it[createdAt] = order.createdAt
        }

        // Insert each item associated with the order
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

    /**
     * Retrieves all orders for a specific user with their items.
     *
     * @param userId The user ID whose orders to fetch.
     * @return List of [OrderEntity] with populated items.
     */
    override suspend fun getOrdersByUser(userId: String): List<OrderEntity> = dbQuery {
        OrdersTable.select { OrdersTable.userId eq userId }.map { row ->
            val orderId = row[OrdersTable.orderId]

            // Fetch all items for this order
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
    }

    /**
     * Retrieves a single order by its ID including its items.
     *
     * @param orderId The ID of the order.
     * @return The [OrderEntity] or null if not found.
     */
    override suspend fun getOrderById(orderId: String): OrderEntity? = dbQuery {
        OrdersTable.select { OrdersTable.orderId eq orderId }.firstNotNullOfOrNull { row ->
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
        }
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId The ID of the order to update.
     * @param newStatus The new status string to set.
     */
    override suspend fun updateOrderStatus(orderId: String, newStatus: String): Unit = dbQuery {
        OrdersTable.update({ OrdersTable.orderId eq orderId }) {
            it[status] = newStatus
        } > 0
    }

    /**
     * Helper function to execute database operations inside a suspended transaction
     * on the IO dispatcher.
     *
     * @param block Lambda with database code to execute.
     * @return The result of the block execution.
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
