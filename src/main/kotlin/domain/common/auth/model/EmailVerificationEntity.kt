package com.commerce.domain.common.auth.model

import com.commerce.data.common.auth.table.UsersTable

data class EmailVerificationEntity(
    val userId : String,
    val email : String,
    val tokenOrOtp : String,
    val expiresAt : String,
    val verified : Boolean
)
