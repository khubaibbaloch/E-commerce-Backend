package com.commerce.domain.services



import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * OrderService handles all database operations related to Orders and OrderItems.
 */
//class OrderService(database: Database) {
//
//    // Table representing Orders (parent)
//    object Orders : Table() {
//        val id = char("id", 36) // UUID
//        val customerId = varchar("customerId", 50)
//        override val primaryKey = PrimaryKey(id)
//    }
//
//    // Table representing each item in an order
//    object OrderItems : Table() {
//        val id = integer("id").autoIncrement()
//        val orderId = varchar("orderId", 36).references(Orders.id)
//        val sku = varchar("sku", 50)
//        val quantity = integer("quantity")
//        override val primaryKey = PrimaryKey(id)
//    }
//
//    init {
//        // Create the schema if not already created
//        transaction(database) {
//            SchemaUtils.create(Orders, OrderItems)
//        }
//    }
//
//    /**
//     * Inserts a new order and its items into the database.
//     */
//    suspend fun create(order: OrderRequestWithId): String = dbQuery {
//        Orders.insert {
//            it[id] = order.orderId
//            it[customerId] = order.customerId
//        }
//
//        order.items.forEach { item ->
//            OrderItems.insert {
//                it[orderId] = order.orderId
//                it[sku] = item.sku
//                it[quantity] = item.quantity
//            }
//        }
//
//        order.orderId
//    }
//
//    /**
//     * Retrieves an order by ID, including its items.
//     */
//    suspend fun read(orderId: String): OrderRequestWithId? = dbQuery {
//        val orderRow = Orders
//            .selectAll()
//            .where { Orders.id eq orderId }
//            .singleOrNull() ?: return@dbQuery null
//
//        val items = OrderItems
//            .selectAll()
//            .where { OrderItems.orderId eq orderId }
//            .map { OrderItem(it[sku], it[quantity]) }
//
//        OrderRequestWithId(
//            orderId = orderId,
//            customerId = orderRow[customerId],
//            items = items
//        )
//    }
//
//    /**
//     * Updates an existing order's customerId and replaces its items.
//     */
//    suspend fun update(orderId: String, order: OrderRequestWithId) = dbQuery {
//        Orders.update({ Orders.id eq orderId }) {
//            it[customerId] = order.customerId
//        }
//
//        OrderItems.deleteWhere { OrderItems.orderId eq orderId }
//
//        order.items.forEach { item ->
//            OrderItems.insert {
//                it[OrderItems.orderId] = orderId
//                it[sku] = item.sku
//                it[quantity] = item.quantity
//            }
//        }
//    }
//
//    /**
//     * Deletes an order and all associated items.
//     */
//    suspend fun delete(orderId: String) = dbQuery {
//        OrderItems.deleteWhere { OrderItems.orderId eq orderId }
//        Orders.deleteWhere { id eq orderId }
//    }
//
//    /**
//     * Utility function to wrap DB operations in a coroutine-friendly context.
//     */
//    private suspend fun <T> dbQuery(block: suspend () -> T): T =
//        newSuspendedTransaction(Dispatchers.IO) { block() }
//}
//
