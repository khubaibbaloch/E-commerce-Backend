package com.commerce.presentation.user.controllers

import com.commerce.data.user.payment.dto.PaymentRequest
import com.commerce.data.user.payment.dto.PaymentResponse
import com.commerce.data.user.payment.mapper.toDomain
import domain.user.payment.usecase.PaymentUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class PaymentController(
    private val paymentUseCase: PaymentUseCase
) {

    suspend fun createPayment(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        val request = call.receive<PaymentRequest>()
        val paymentModel = request.toDomain()
        val paymentId = paymentUseCase.createPayment(paymentModel, userId)

        call.respond(HttpStatusCode.Created, mapOf("paymentId" to paymentId))
    }

    suspend fun getPayments(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        val payments = paymentUseCase.getPayments(userId)
        val response = payments.map {
            PaymentResponse(
                paymentId = it.paymentId,
                orderId = it.orderId,
                amount = it.amount,
                status = it.status,
                paymentMethod = it.paymentMethod,
                createdAt = it.createdAt
            )
        }

        call.respond(HttpStatusCode.OK, response)
    }
}
