package com.commerce.data.order.table

import org.jetbrains.exposed.sql.Table

object OrderItemsTable : Table("order_items") {
    val id = integer("id").autoIncrement()
    val orderId = varchar("orderId", 36).references(OrdersTable.orderId)
    val productId = varchar("productId", 36)
    val quantity = integer("quantity")
    val price = double("price")

    override val primaryKey = PrimaryKey(id)
}
