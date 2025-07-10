package com.commerce.domain.admin.repository

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.model.UserAdminEntity

/**
 * Repository interface for admin-level access to user and product data.
 * Provides methods for retrieving system-wide user and product information.
 */
interface AdminRepository {

    /**
     * Fetches all registered users in the system.
     * Intended for administrative viewing, auditing, or management tasks.
     *
     * @return List of [UserAdminEntity] representing all users.
     */
    suspend fun getAllUsers(): List<UserAdminEntity>

    /**
     * Retrieves all products listed by all sellers.
     * Gives admins a complete view of the product catalog.
     *
     * @return List of [ProductAdminEntity] representing all products in the database.
     */
    suspend fun getAllProducts(): List<ProductAdminEntity>
}
