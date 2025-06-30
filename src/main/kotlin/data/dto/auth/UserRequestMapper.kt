package com.commerce.data.dto.auth

import domain.models.UserEntity


fun UserRequest.toDomain(): UserEntity {
    return UserEntity(
        username = this.username,
        password = this.password
    )
}
