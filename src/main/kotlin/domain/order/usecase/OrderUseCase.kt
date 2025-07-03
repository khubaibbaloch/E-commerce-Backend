package com.commerce.domain.order.usecase

import domain.order.usecase.CancelOrderUseCase
import domain.order.usecase.GetOrdersByUserUseCase

data class OrderUseCase(
    val placeOrder: PlaceOrderUseCase,
    val getOrdersByUser: GetOrdersByUserUseCase,
    val cancelOrder: CancelOrderUseCase
)