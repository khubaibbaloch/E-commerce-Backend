package com.commerce.data.db.tables

import com.commerce.data.db.tables.UsersTable.autoIncrement
import com.commerce.data.db.tables.UsersTable.uniqueIndex
import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val id = integer("id").autoIncrement()
    val userid = varchar("userid", 36).uniqueIndex()
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 64)

    override val primaryKey = PrimaryKey(id)
}

