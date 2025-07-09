package com.commerce.domain.admin.service

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity

interface AdminService {
    suspend fun getAllUsers(): List<UserAdminEntity>
    suspend fun getAllProducts(): List<ProductAdminEntity>
}