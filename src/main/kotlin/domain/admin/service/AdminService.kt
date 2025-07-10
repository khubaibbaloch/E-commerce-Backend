package com.commerce.domain.admin.service

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity

/**
 * Service interface that defines business logic for admin operations.
 * Acts as an abstraction layer between the controller/use case and the admin data layer.
 */
interface AdminService {

    /**
     * Retrieves a complete list of all users in the system.
     * Useful for admin dashboards, audits, and user management features.
     *
     * @return List of [UserAdminEntity] containing user metadata.
     */
    suspend fun getAllUsers(): List<UserAdminEntity>

    /**
     * Retrieves a complete list of all products across all sellers.
     * Enables administrative monitoring and management of product listings.
     *
     * @return List of [ProductAdminEntity] containing product metadata.
     */
    suspend fun getAllProducts(): List<ProductAdminEntity>
}
