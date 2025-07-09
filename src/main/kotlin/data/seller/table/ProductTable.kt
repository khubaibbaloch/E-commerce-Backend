package com.commerce.data.seller.table

import org.jetbrains.exposed.sql.Table

/**
 * Represents the 'products' table in the database.
 * Stores product details added by sellers.
 */
object ProductTable : Table("products") {

    // Auto-incremented primary key (used internally)
    val id = integer("id").autoIncrement()

    // Unique public identifier for the product (UUID format recommended)
    val productId = varchar("productId", 36).uniqueIndex()

    // Seller's unique ID (foreign key from users table)
    val sellerId = varchar("sellerId", 36)

    // Name of the product (up to 50 characters)
    val name = varchar("name", 50)

    // Detailed product description (up to 255 characters)
    val description = varchar("description", 255)

    // Price of the product (double-precision float)
    val price = double("price")

    // Available quantity in stock
    val quantity = integer("quantity")

    // Set the primary key of this table to the internal 'id'
    override val primaryKey = PrimaryKey(id)
}
