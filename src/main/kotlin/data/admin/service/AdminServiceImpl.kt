package com.commerce.data.admin.service

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity
import com.commerce.domain.admin.repository.AdminRepository
import com.commerce.domain.admin.service.AdminService

class AdminServiceImpl(private val adminRepository: AdminRepository):AdminService {
    override suspend fun getAllUsers(): List<UserAdminEntity> {
        return adminRepository.getAllUsers()
    }

    override suspend fun getAllProducts(): List<ProductAdminEntity> {
        return adminRepository.getAllProducts()
    }
}