package com.commerce.data.admin.service

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity
import com.commerce.domain.admin.repository.AdminRepository
import com.commerce.domain.admin.service.AdminService

/**
 * Implementation of AdminService interface.
 * Responsible for handling business logic related to admin functionalities.
 * Delegates data fetching to the AdminRepository.
 */
class AdminServiceImpl(
    private val adminRepository: AdminRepository
) : AdminService {

    /**
     * Fetch all users in the system.
     * Used by admin to view/manage user accounts.
     */
    override suspend fun getAllUsers(): List<UserAdminEntity> {
        return adminRepository.getAllUsers()
    }

    /**
     * Fetch all products added by sellers.
     * Allows admin to audit and monitor product listings.
     */
    override suspend fun getAllProducts(): List<ProductAdminEntity> {
        return adminRepository.getAllProducts()
    }
}
