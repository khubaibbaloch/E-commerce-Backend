package com.commerce.data.seller.table

import org.jetbrains.exposed.sql.Table

object ProductTable : Table("products") {
    val id = integer("id").autoIncrement()
    val productId = varchar("productId", 36).uniqueIndex()
    val sellerId = varchar("sellerId", 36)
    val name = varchar("name", 50)
    val description = varchar("description", 255)
    val price = double("price")
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(id)
}