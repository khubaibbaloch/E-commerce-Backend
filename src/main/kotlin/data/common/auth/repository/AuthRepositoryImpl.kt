package com.commerce.data.common.auth.repository

import com.commerce.data.common.auth.table.UsersTable
import com.commerce.domain.auth.model.UserEntity
import com.commerce.domain.auth.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AuthRepositoryImpl(private val database: Database) : AuthRepository {

    init {
        transaction(database) {
            //SchemaUtils.drop(UsersTable)
            SchemaUtils.create(UsersTable)
        }
    }

    override suspend fun insertUser(userEntity: UserEntity): String = dbQuery {
        val generatedUUID = UUID.randomUUID().toString()
        UsersTable.insert {
            it[userid] = generatedUUID
            it[username] = userEntity.username
            it[password] = userEntity.password
        }
        generatedUUID
    }

    override suspend fun findUser(username: String): ResultRow? = dbQuery {
        UsersTable.select { UsersTable.username eq username }.singleOrNull()
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}