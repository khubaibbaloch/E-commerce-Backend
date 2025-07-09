package com.commerce.data.payment.table

import com.commerce.data.order.table.OrdersTable
import org.jetbrains.exposed.sql.Table

object PaymentsTable : Table("payments") {
    val paymentId = varchar("paymentId", 36).uniqueIndex()
    val orderId = varchar("orderId", 36).references(OrdersTable.orderId)
    val userId = varchar("userId", 36)
    val amount = double("amount")
    val status = varchar("status", 20) // PENDING, SUCCESS, FAILED
    val paymentMethod = varchar("paymentMethod", 20)
    val createdAt = long("createdAt")

    override val primaryKey = PrimaryKey(paymentId)
}
