package com.commerce.data.admin.repository

import com.commerce.data.common.auth.table.UsersTable
import com.commerce.data.seller.table.ProductTable
import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity
import com.commerce.domain.admin.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AdminRepositoryImpl(private val database: Database): AdminRepository {
    override suspend fun getAllUsers(): List<UserAdminEntity> {
        return dbQuery {
            UsersTable.selectAll().map { row ->
                UserAdminEntity(
                    id = row[UsersTable.id],
                    userid = row[UsersTable.userid],
                    username = row[UsersTable.username],
                    password = row[UsersTable.password],
                )
            }
        }
    }

    override suspend fun getAllProducts(): List<ProductAdminEntity> = dbQuery {
        ProductTable.selectAll()
            .map {
                ProductAdminEntity(
                    id = it[ProductTable.id],
                    productId = it[ProductTable.productId],
                    sellerId = it[ProductTable.sellerId],
                    name = it[ProductTable.name],
                    description = it[ProductTable.description],
                    price = it[ProductTable.price],
                    quantity = it[ProductTable.quantity]
                )
            }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}