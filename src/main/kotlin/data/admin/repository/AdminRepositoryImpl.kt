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

/**
 * Implementation of AdminRepository for handling admin-level database queries.
 * Uses Exposed ORM with coroutine support.
 */
class AdminRepositoryImpl(private val database: Database) : AdminRepository {

    /**
     * Retrieves all users from the UsersTable.
     * This is an admin-only operation for managing users.
     */
    override suspend fun getAllUsers(): List<UserAdminEntity> {
        return dbQuery {
            UsersTable.selectAll().map { row ->
                UserAdminEntity(
                    id = row[UsersTable.id],                 // Internal DB ID
                    userid = row[UsersTable.userid],         // Unique public user ID (UUID or string)
                    username = row[UsersTable.username],     // User's login name
                    password = row[UsersTable.password]      // ⚠️ Stored password (should be hashed)
                )
            }
        }
    }

    /**
     * Retrieves all products from the ProductTable.
     * Useful for administrative product auditing and visibility.
     */
    override suspend fun getAllProducts(): List<ProductAdminEntity> = dbQuery {
        ProductTable.selectAll().map { row ->
            ProductAdminEntity(
                id = row[ProductTable.id],                   // Internal DB ID
                productId = row[ProductTable.productId],     // Public product identifier
                sellerId = row[ProductTable.sellerId],       // Reference to seller user ID
                name = row[ProductTable.name],               // Product name
                description = row[ProductTable.description], // Description/details
                price = row[ProductTable.price],             // Price of product
                quantity = row[ProductTable.quantity]        // Stock available
            )
        }
    }

    /**
     * Wrapper to execute a DB operation inside a coroutine-safe transaction.
     * Uses IO dispatcher for non-blocking DB access.
     */
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}
