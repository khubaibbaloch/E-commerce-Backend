package com.commerce.data.db.repository

import com.commerce.data.db.tables.auth.UsersTable
import com.commerce.domain.models.auth.UserEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AuthRepository(private val database: Database) {

    init {
        transaction(database) {
            //SchemaUtils.drop(UsersTable)
            SchemaUtils.create(UsersTable)
        }
    }

    suspend fun findUser(username: String): ResultRow? = dbQuery {
        UsersTable.select { UsersTable.username eq username }.singleOrNull()
    }

    suspend fun insertUser(userEntity: UserEntity): String = dbQuery {
        val generatedUUID = UUID.randomUUID().toString()
        UsersTable.insert {
            it[userid] = generatedUUID
            it[username] = userEntity.username
            it[password] = userEntity.password
        }
        generatedUUID
    }
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}