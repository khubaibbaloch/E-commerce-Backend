package com.commerce.data.auth.mapper

import com.commerce.data.auth.dto.UserRequest
import com.commerce.domain.auth.model.UserEntity


fun UserRequest.toDomain(): UserEntity {
    return UserEntity(
        username = this.username,
        password = this.password
    )
}
