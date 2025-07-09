package com.commerce.domain.order.usecase

import domain.user.order.usecase.CancelOrderUseCase
import domain.user.order.usecase.GetOrdersByUserUseCase

data class OrderUseCase(
    val placeOrder: PlaceOrderUseCase,
    val getOrdersByUser: GetOrdersByUserUseCase,
    val cancelOrder: CancelOrderUseCase
)