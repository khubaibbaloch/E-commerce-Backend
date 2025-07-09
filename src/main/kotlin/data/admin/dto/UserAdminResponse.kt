package data.admin.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserAdminResponse(
    val id: Int,
    val userid: String,
    val username: String,
    val password: String
)