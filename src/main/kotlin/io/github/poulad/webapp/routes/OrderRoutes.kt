package io.github.poulad.webapp.routes

import io.github.poulad.webapp.models.OrderTotalDto
import io.github.poulad.webapp.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.listOrderRoutes() {
    get("$BASE_API_ROUTE/orders") {
        if (orderStorage.isEmpty()) {
            call.respond(HttpStatusCode.NoContent)
            return@get
        }
        call.respond(orderStorage)
    }
}

fun Route.getOrderRoute() {
    get("$BASE_API_ROUTE/orders/{id?}") {
        val id = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)

        val order = orderStorage.find { it.number == id }
            ?: return@get call.respond(HttpStatusCode.NotFound)

        call.respond(order)
    }
}

fun Route.totalizeOrderRoute() {
    get("$BASE_API_ROUTE/orders/{id}/total") {
        val id = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)

        val order = orderStorage.find { it.number == id }
            ?: return@get call.respond(HttpStatusCode.NotFound)

        val total = order.content.sumOf { it.amount * it.price }
        return@get call.respond(OrderTotalDto(total))
    }
}
