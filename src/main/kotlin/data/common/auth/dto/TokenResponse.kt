package com.commerce.data.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(val token: String)