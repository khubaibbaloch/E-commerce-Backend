package com.commerce.data.common.auth.table

import org.jetbrains.exposed.sql.Table


/**
 * Table to store temporary email verification tokens or OTPs for users.
 */
object EmailVerificationsTable : Table("email_verifications") {

    val id = integer("id").autoIncrement()
    val userId = varchar("user_id", 36).references(UsersTable.userid)
   // val email = varchar("email", 36).references(UsersTable.)
    val tokenOrOtp = varchar("token_or_otp", 64).uniqueIndex()
    val expiresAt = varchar("expires_at",64)
    val verified = bool("verified").default(false)

    override val primaryKey = PrimaryKey(id)
}
