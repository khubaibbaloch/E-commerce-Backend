package com.commerce.data.payment.repository

import com.commerce.data.payment.table.PaymentsTable
import com.commerce.domain.payment.model.PaymentEntity
import com.commerce.domain.payment.repository.PaymentRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class PaymentRepositoryImpl(private val database: Database):PaymentRepository {
    init {
        transaction(database) {
            SchemaUtils.create(PaymentsTable)
        }
    }

    override suspend fun createPayment(payment: PaymentEntity): String = dbQuery {
        PaymentsTable.insert {
            it[paymentId] = payment.paymentId
            it[orderId] = payment.orderId
            it[userId] = payment.userId
            it[amount] = payment.amount
            it[status] = payment.status
            it[paymentMethod] = payment.paymentMethod
            it[createdAt] = payment.createdAt
        }
        payment.paymentId
    }

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

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
