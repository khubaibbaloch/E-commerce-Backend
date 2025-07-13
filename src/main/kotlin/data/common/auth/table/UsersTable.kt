package com.commerce.data.common.auth.table

import org.jetbrains.exposed.sql.Table

/**
 * UsersTable represents the schema for storing user credentials and identity data.
 * This table is used across roles (user, seller, admin) for authentication and identification.
 */
object UsersTable : Table("users") {

    // Auto-incremented internal database ID (Primary Key)
    val id = integer("id").autoIncrement()

    // Universally Unique Identifier for the user, used in JWT and APIs
    val userid = varchar("userid", 36).uniqueIndex()

    // Username used for login; must be unique
    val username = varchar("username", 50).uniqueIndex()

    val email = varchar("email", 50).uniqueIndex()

    // Hashed password string (length 64 for SHA-256, adjust if using other hashing)
    val password = varchar("password", 64)

    // Define primary key constraint on 'id'
    override val primaryKey = PrimaryKey(id)
}
