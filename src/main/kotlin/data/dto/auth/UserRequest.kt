package com.commerce.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val username: String, val password: String)