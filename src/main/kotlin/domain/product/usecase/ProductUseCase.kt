package domain.product.usecase

data class ProductUseCase(
    val insert: InsertProductUseCase,
    val findByName: FindProductByNameUseCase,
    val getAll: GetAllProductsUseCase,
    val updateById: UpdateProductByIdUseCase,
    val deleteById: DeleteProductByIdUseCase
)
