package com.commerce.domain.admin.repository

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity

interface AdminRepository {
    suspend fun getAllUsers(): List<UserAdminEntity>
    suspend fun getAllProducts(): List<ProductAdminEntity>
}