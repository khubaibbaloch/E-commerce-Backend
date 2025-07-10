package com.commerce.data.user.cart.table

import com.commerce.data.seller.table.ProductTable
import org.jetbrains.exposed.sql.Table

/**
 * Table definition for the user's shopping cart.
 * Stores individual cart entries including user and product references.
 */
object CartTable : Table("cart") {

    // Auto-incremented internal database ID (primary key)
    val id = integer("id").autoIncrement()

    // UUID string used as a public identifier for the cart item
    val cartId = varchar("cartId", 36).uniqueIndex()

    // References the user who owns this cart entry (UUID)
    val userId = varchar("userId", 36)

    // Foreign key reference to the product in the seller's product table
    val productId = varchar("productId", 36).references(ProductTable.productId)

    // Quantity of the selected product
    val quantity = integer("quantity")

    // Sets the primary key for the table to the internal ID
    override val primaryKey = PrimaryKey(id)
}
