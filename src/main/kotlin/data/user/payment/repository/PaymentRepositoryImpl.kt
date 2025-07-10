package com.commerce.data.user.payment.repository

import com.commerce.data.user.payment.table.PaymentsTable
import com.commerce.domain.payment.model.PaymentEntity
import com.commerce.domain.payment.repository.PaymentRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Implementation of the PaymentRepository interface.
 * Handles all database operations related to user payments using Exposed ORM.
 */
class PaymentRepositoryImpl(private val database: Database) : PaymentRepository {

    init {
        // Ensure the PaymentsTable is created in the database at startup
        transaction(database) {
            SchemaUtils.create(PaymentsTable)
        }
    }

    /**
     * Inserts a new payment record into the database.
     * @param payment The payment domain entity to store.
     * @return The paymentId of the newly created payment.
     */
    override suspend fun createPayment(payment: PaymentEntity): String = dbQuery {
        PaymentsTable.insert {
            it[paymentId] = payment.paymentId              // Unique ID for the payment
            it[orderId] = payment.orderId                  // Related order ID
            it[userId] = payment.userId                    // ID of the user making the payment
            it[amount] = payment.amount                    // Total payment amount
            it[status] = payment.status                    // Payment status (e.g., COMPLETED)
            it[paymentMethod] = payment.paymentMethod      // Payment method (e.g., CARD, COD)
            it[createdAt] = payment.createdAt              // Timestamp of the transaction
        }
        payment.paymentId
    }

    /**
     * Retrieves a list of payments made by a specific user.
     * @param userId The ID of the user to query.
     * @return A list of PaymentEntity objects.
     */
    override suspend fun getPaymentsByUser(userId: String): List<PaymentEntity> = dbQuery {
        PaymentsTable.select { PaymentsTable.userId eq userId }
            .map {
                PaymentEntity(
                    paymentId = it[PaymentsTable.paymentId],
                    orderId = it[PaymentsTable.orderId],
                    userId = it[PaymentsTable.userId],
                    amount = it[PaymentsTable.amount],
                    status = it[PaymentsTable.status],
                    paymentMethod = it[PaymentsTable.paymentMethod],
                    createdAt = it[PaymentsTable.createdAt]
                )
            }
    }

    /**
     * Executes a database query in a coroutine-safe way using Exposed and IO dispatcher.
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
