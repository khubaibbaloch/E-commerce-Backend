package com.commerce.domain.admin.usecase

import com.commerce.domain.admin.model.UserAdminEntity
import com.commerce.domain.admin.service.AdminService

/**
 * Use case for retrieving all registered users.
 * This operation is intended for admin-level access and visibility.
 *
 * It delegates the actual fetching logic to the [AdminService],
 * which interacts with the repository layer.
 *
 * @property adminService Service handling admin-related operations.
 */
class GetAllUserUseCase(
    private val adminService: AdminService
) {
    /**
     * Invokes the use case to return a list of all users in the system.
     *
     * @return List of [UserAdminEntity] representing all registered users.
     */
    suspend operator fun invoke(): List<UserAdminEntity> {
        return adminService.getAllUsers()
    }
}
