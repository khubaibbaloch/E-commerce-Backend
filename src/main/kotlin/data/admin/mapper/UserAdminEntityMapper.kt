package com.commerce.data.admin.mapper

import com.commerce.domain.admin.model.UserAdminEntity
import data.admin.dto.UserAdminResponse

/**
 * Extension function to map UserAdminEntity (domain model)
 * into UserAdminResponse (DTO for admin-facing API).
 */
fun UserAdminEntity.toResponse(): UserAdminResponse {
    return UserAdminResponse(
        id = this.id,               // Internal DB ID (primary key)
        userid = this.userid,       // Unique user identifier (UUID or public ID)
        username = this.username,   // Login username
        password = this.password    // ⚠️ Hashed password (ensure it's not exposed in public APIs)
    )
}
