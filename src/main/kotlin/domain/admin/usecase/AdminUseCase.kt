package com.commerce.domain.admin.usecase

import domain.user.product.usecase.GetAllProductsUseCase

data class AdminUseCase(
    val getAllUser: GetAllUserUseCase,
    val getAllProducts: GetAllProductsUseCase
)