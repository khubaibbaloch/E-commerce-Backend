package com.commerce.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(val token: String)