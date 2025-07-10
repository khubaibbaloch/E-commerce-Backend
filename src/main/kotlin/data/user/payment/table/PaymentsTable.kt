package com.commerce.data.user.payment.table

import com.commerce.data.user.order.table.OrdersTable
import org.jetbrains.exposed.sql.Table

/**
 * Exposed table definition for storing payment records.
 * Represents the "payments" table in the database.
 */
object PaymentsTable : Table("payments") {

    // Unique ID for each payment (UUID)
    val paymentId = varchar("paymentId", 36).uniqueIndex()

    // Foreign key referencing the order this payment belongs to
    val orderId = varchar("orderId", 36).references(OrdersTable.orderId)

    // ID of the user who made the payment
    val userId = varchar("userId", 36)

    // Amount paid for the order
    val amount = double("amount")

    // Current status of the payment (e.g., PENDING, SUCCESS, FAILED)
    val status = varchar("status", 20)

    // Method used for payment (e.g., CREDIT_CARD, COD, PAYPAL)
    val paymentMethod = varchar("paymentMethod", 20)

    // Timestamp of when the payment was made (stored as epoch milliseconds)
    val createdAt = long("createdAt")

    // Set paymentId as the primary key for the table
    override val primaryKey = PrimaryKey(paymentId)
}
