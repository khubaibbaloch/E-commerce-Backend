package com.commerce.presentation.user.controllers

import com.commerce.data.product.dto.ProductRequest
import com.commerce.data.product.dto.ProductUpdateRequest
import com.commerce.data.seller.mapper.toDomain
import com.commerce.data.user.product.mapper.toResponse
import domain.user.product.usecase.UserProductUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UserProductController(
    private val userProductUseCase: UserProductUseCase
) {

    suspend fun ping(call: ApplicationCall) {
        call.respond(mapOf("message" to "Products route is working"))
    }


    suspend fun getAllProducts(call: ApplicationCall) {
        val productList = userProductUseCase.getAll()
        call.respond(HttpStatusCode.OK, productList.map { it.toResponse() })
    }

    suspend fun searchProductByName(call: ApplicationCall) {
        val productName = call.parameters["name"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Product name is required")

        val productList = userProductUseCase.findByName(productName)
        call.respond(HttpStatusCode.OK, productList.map { it.toResponse() })
    }

}
