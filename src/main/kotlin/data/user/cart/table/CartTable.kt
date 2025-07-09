package com.commerce.data.cart.table

import com.commerce.data.seller.table.ProductTable
import org.jetbrains.exposed.sql.Table

object CartTable : Table("cart") {
    val id = integer("id").autoIncrement()
    val cartId = varchar("cartId", 36).uniqueIndex()
    val userId = varchar("userId", 36)
    val productId = varchar("productId", 36).references(ProductTable.productId)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(id)
}
