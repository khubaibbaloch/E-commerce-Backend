package com.commerce.presentation.routes

import com.commerce.data.dto.payment.toDomain
import data.dto.payment.PaymentRequest
import data.dto.payment.PaymentResponse
import domain.services.PaymentService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.text.get

fun Route.paymentRoutes(paymentService: PaymentService) {
    route("/payments") {

        post {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
                ?: return@post call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

            val request = call.receive<PaymentRequest>()
            val updateModel = request.toDomain()
            val paymentId = paymentService.createPayment(updateModel, userId)
            call.respond(HttpStatusCode.Created, mapOf("paymentId" to paymentId))
        }

        get {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

            val payments = paymentService.getPayments(userId)
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
}
