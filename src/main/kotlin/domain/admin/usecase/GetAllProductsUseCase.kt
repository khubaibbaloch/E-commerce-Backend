package domain.admin.usecase

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.service.AdminService

/**
 * Use case for retrieving all products from an administrative perspective.
 * Delegates the operation to the AdminService which contains business logic.
 *
 * This use case allows admins to fetch product data across all sellers.
 *
 * @property adminService Service layer handling admin product logic.
 */
class GetAllProductsUseCase(
    private val adminService: AdminService
) {
    /**
     * Executes the use case to get a list of all products.
     *
     * @return List of [ProductAdminEntity] representing all products in the system.
     */
    suspend operator fun invoke(): List<ProductAdminEntity> {
        return adminService.getAllProducts()
    }
}
