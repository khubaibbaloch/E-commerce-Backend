package domain.user.product.usecase

data class UserProductUseCase(
    val findByName: FindProductByNameUseCase,
    val getAll: GetAllProductsUseCase,
)
