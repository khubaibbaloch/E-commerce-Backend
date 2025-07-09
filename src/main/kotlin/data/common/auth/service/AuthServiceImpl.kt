package com.commerce.data.auth.service

import com.commerce.data.common.auth.table.UsersTable
import com.commerce.domain.auth.model.UserEntity
import com.commerce.domain.auth.repository.AuthRepository
import com.commerce.domain.auth.service.AuthService


class AuthServiceImpl(private val repository: AuthRepository):AuthService{

    override suspend fun registerAndReturnUserId(userEntity: UserEntity): String? {
        val existing = repository.findUser(userEntity.username)
        return if (existing != null) null else repository.insertUser(userEntity)
    }


    override suspend fun login(userEntity: UserEntity): String? {
        val user = repository.findUser(userEntity.username)

        return if (user != null && user[UsersTable.password] == userEntity.password) {
            user[UsersTable.userid]
        } else {
            null
        }
    }

}
