package com.commerce.data.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequestWithId(val userId: String, val username: String, val password: String)