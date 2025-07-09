package com.commerce.domain.admin.usecase

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity
import com.commerce.domain.admin.service.AdminService

class GetAllUserUseCase(private val adminService: AdminService) {
    suspend operator fun invoke (): List<UserAdminEntity>{
        return adminService.getAllUsers()
    }
}
