package com.commerce.data.common.auth.table

import org.jetbrains.exposed.sql.Table

/**
 * Table schema for storing email verification tokens or OTPs.
 * Used to manage email verification status per user during signup/login.
 */
object EmailVerificationsTable : Table("email_verifications") {

    // Unique auto-incremented primary key
    val id = integer("id").autoIncrement()

    // User ID (foreign key from Users table) — must be unique per user for verification
    val userId = varchar("user_id", 36)
        .references(UsersTable.userid) // 🔗 Foreign key to Users table
        .uniqueIndex()                 // 🧍 One-to-one verification record per user

    // Email address tied to verification
    val email = varchar("email", 36)

    // Either OTP or token (used for both OTP and email link)
    val tokenOrOtp = varchar("token_or_otp", 64)
        .uniqueIndex()                // 🧾 Ensures no duplicate tokens

    // ISO formatted timestamp string of expiry (e.g. "2025-07-11T10:30:00")
    val expiresAt = varchar("expires_at", 64)

    // Boolean flag to mark if user has verified this email
    val verified = bool("verified").default(false)

    // Primary key definition
    override val primaryKey = PrimaryKey(id)
}
