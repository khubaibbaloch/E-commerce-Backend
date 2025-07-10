package com.commerce.presentation.user.routes

import domain.user.payment.usecase.PaymentUseCase
import io.ktor.server.routing.*
import com.commerce.presentation.user.controllers.PaymentController

/**
 * Defines the HTTP routes for handling payment-related operations.
 * This includes creating a payment and retrieving a user's payment history.
 *
 * Base path: /payments
 */
fun Route.paymentRoutes(paymentUseCase: PaymentUseCase) {
    // Initialize the controller with the required use case
    val controller = PaymentController(paymentUseCase)

    // Group all payment-related routes under "/payments"
    route("/payments") {

        // POST /payments - Create a new payment for an existing order
        post { controller.createPayment(call) }

        // GET /payments - Retrieve all payments made by the authenticated user
        get { controller.getPayments(call) }
    }
}
