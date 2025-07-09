package domain.admin.usecase

import com.commerce.domain.admin.model.ProductAdminEntity
import com.commerce.domain.admin.service.AdminService

class GetAllProductsUseCase(private val adminService: AdminService) {
    suspend operator fun invoke ():  List<ProductAdminEntity>{
        return adminService.getAllProducts()
    }
}