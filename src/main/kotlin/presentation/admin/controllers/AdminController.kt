package com.commerce.presentation.admin.controllers

import com.commerce.data.admin.mapper.toResponse
import com.commerce.data.user.product.mapper.toResponse
import com.commerce.domain.admin.usecase.AdminUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class AdminController(private val adminUseCase: AdminUseCase) {
    suspend fun getAllUsers(call: ApplicationCall){
        val allUsers = adminUseCase.getAllUser()
        val response = allUsers.map { it.toResponse() }
        call.respond(HttpStatusCode.OK, response)
    }
    suspend fun getAllProducts(call: ApplicationCall){
        val allProducts = adminUseCase.getAllProducts()
        val response = allProducts.map { it.toResponse() }
        call.respond(HttpStatusCode.OK, response)
    }
}