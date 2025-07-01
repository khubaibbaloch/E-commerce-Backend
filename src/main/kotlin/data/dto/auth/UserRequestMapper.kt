package com.commerce.data.dto.auth

import com.commerce.domain.models.auth.UserEntity


fun UserRequest.toDomain(): UserEntity {
    return UserEntity(
        username = this.username,
        password = this.password
    )
}
