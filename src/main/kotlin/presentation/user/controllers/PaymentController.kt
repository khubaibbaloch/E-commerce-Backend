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

/**
 * Controller responsible for handling payment-related operations for the user.
 * Supports payment creation and fetching payment history.
 */
class PaymentController(
    private val paymentUseCase: PaymentUseCase
) {

    /**
     * Creates a new payment for an order made by the authenticated user.
     * Requires: JWT with userId
     * Body: PaymentRequest (contains orderId, payment method, and status)
     * Returns: paymentId in response if created successfully
     */
    suspend fun createPayment(call: ApplicationCall) {
        // Get userId from JWT
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        // Receive request body and map to domain model
        val request = call.receive<PaymentRequest>()
        val paymentModel = request.toDomain()

        // Call use case to handle payment logic
        val paymentId = paymentUseCase.createPayment(paymentModel, userId)

        // Respond with created payment ID
        call.respond(HttpStatusCode.Created, mapOf("paymentId" to paymentId))
    }

    /**
     * Retrieves all payment transactions made by the authenticated user.
     * Requires: JWT with userId
     * Returns: List of PaymentResponse objects
     */
    suspend fun getPayments(call: ApplicationCall) {
        // Get userId from JWT
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        // Fetch payments from domain use case
        val payments = paymentUseCase.getPayments(userId)

        // Map domain model to DTO for response
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

        // Respond with payment list
        call.respond(HttpStatusCode.OK, response)
    }
}
