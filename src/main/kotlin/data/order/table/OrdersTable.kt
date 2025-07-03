package com.commerce.data.order.table

import org.jetbrains.exposed.sql.Table

object OrdersTable : Table("orders") {
    val orderId = varchar("orderId", 36).uniqueIndex()
    val userId = varchar("userId", 36)
    val totalPrice = double("totalPrice")
    val status = varchar("status", 20)
    val createdAt = long("createdAt")

    override val primaryKey = PrimaryKey(orderId)
}
