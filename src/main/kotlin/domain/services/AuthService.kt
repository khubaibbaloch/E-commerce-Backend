package com.commerce.domain.services

import com.commerce.data.db.repository.AuthRepository
import com.commerce.data.db.tables.UsersTable
import domain.models.UserEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


class AuthService(private val authRepository: AuthRepository) {

    suspend fun registerAndReturnUserId(userEntity: UserEntity): String? {
        val existing = authRepository.findUser(userEntity.username)
        return if (existing != null) null else authRepository.insertUser(userEntity)
    }


    suspend fun login(userEntity: UserEntity): String? {
        val user = authRepository.findUser(userEntity.username)

        return if (user != null && user[UsersTable.password] == userEntity.password) {
            user[UsersTable.userid]
        } else {
            null
        }
    }

}
