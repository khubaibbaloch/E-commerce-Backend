package com.commerce.data.user.order.table

import org.jetbrains.exposed.sql.Table

/**
 * Table schema representing items within an order.
 * Each row corresponds to a product included in a specific order.
 */
object OrderItemsTable : Table("order_items") {

    /** Primary key, auto-incremented integer ID */
    val id = integer("id").autoIncrement()

    /** Foreign key referencing the parent order's ID */
    val orderId = varchar("orderId", 36).references(OrdersTable.orderId)

    /** ID of the product included in the order */
    val productId = varchar("productId", 36)

    /** Quantity of the product ordered */
    val quantity = integer("quantity")

    /** Price per unit of the product at time of order */
    val price = double("price")

    /** Composite primary key set on the auto-incremented ID */
    override val primaryKey = PrimaryKey(id)
}
