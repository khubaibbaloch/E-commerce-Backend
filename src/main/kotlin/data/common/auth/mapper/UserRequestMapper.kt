package com.commerce.data.common.auth.mapper

import com.commerce.data.common.auth.dto.UserRequest
import com.commerce.domain.auth.model.UserEntity

/**
 * Extension function to map a UserRequest DTO to a domain-level UserEntity.
 * This is typically used during authentication flows (login, registration).
 */
fun UserRequest.toDomain(): UserEntity {
    return UserEntity(
        username = this.username,  // Maps DTO username to domain model
        password = this.password   // Maps DTO password to domain model (plain or hashed later)
    )
}
