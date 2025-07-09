package com.commerce.data.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val username: String, val password: String)