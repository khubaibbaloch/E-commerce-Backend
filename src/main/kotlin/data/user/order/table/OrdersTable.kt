package com.commerce.data.user.order.table

import org.jetbrains.exposed.sql.Table

/**
 * Table schema representing orders placed by users.
 * Each row corresponds to a single order.
 */
object OrdersTable : Table("orders") {

    /** Unique identifier for each order (UUID string) */
    val orderId = varchar("orderId", 36).uniqueIndex()

    /** Identifier of the user who placed the order */
    val userId = varchar("userId", 36)

    /** Total price of the entire order */
    val totalPrice = double("totalPrice")

    /** Current status of the order (e.g., PENDING, COMPLETED, CANCELLED) */
    val status = varchar("status", 20)

    /** Timestamp of when the order was created, stored as epoch milliseconds */
    val createdAt = long("createdAt")

    /** Primary key for the orders table */
    override val primaryKey = PrimaryKey(orderId)
}
