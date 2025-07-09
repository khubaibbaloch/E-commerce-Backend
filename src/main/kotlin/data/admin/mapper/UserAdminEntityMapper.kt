package com.commerce.data.admin.mapper

import com.commerce.domain.admin.model.UserAdminEntity
import data.admin.dto.UserAdminResponse

fun UserAdminEntity.toResponse(): UserAdminResponse {
    return UserAdminResponse(
        id = this.id,
        userid = this.userid,
        username = this.username,
        password = this.password
    )
}