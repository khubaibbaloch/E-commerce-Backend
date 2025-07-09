package com.commerce.presentation.user.routes

import domain.user.payment.usecase.PaymentUseCase
import io.ktor.server.routing.*
import com.commerce.presentation.user.controllers.PaymentController

//fun Route.paymentRoutes(paymentService: PaymentService) {
//    route("/payments") {
//
//        post {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//                ?: return@post call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
//
//            val request = call.receive<PaymentRequest>()
//            val updateModel = request.toDomain()
//            val paymentId = paymentService.createPayment(updateModel, userId)
//            call.respond(HttpStatusCode.Created, mapOf("paymentId" to paymentId))
//        }
//
//        get {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
//
//            val payments = paymentService.getPayments(userId)
//            val response = payments.map {
//                PaymentResponse(
//                    paymentId = it.paymentId,
//                    orderId = it.orderId,
//                    amount = it.amount,
//                    status = it.status,
//                    paymentMethod = it.paymentMethod,
//                    createdAt = it.createdAt
//                )
//            }
//            call.respond(HttpStatusCode.OK, response)
//        }
//    }
//}

fun Route.paymentRoutes(paymentUseCase: PaymentUseCase) {
    val controller = PaymentController(paymentUseCase)

    route("/payments") {
        post { controller.createPayment(call) }
        get { controller.getPayments(call) }

    }
}