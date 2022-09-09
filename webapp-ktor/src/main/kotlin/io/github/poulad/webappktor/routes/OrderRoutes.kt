package io.github.poulad.webappktor.routes

import io.github.poulad.webappktor.models.OrderTotalDto
import io.github.poulad.webappktor.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.listOrderRoutes() {

    route("$BASE_API_ROUTE/orders", HttpMethod.Get) {
        handle {
            if (orderStorage.isEmpty()) {
                return@handle call.respond(HttpStatusCode.NoContent)
            }
            return@handle call.respond(orderStorage)
        }
    }
}

fun Route.getOrderRoute() {
    get("$BASE_API_ROUTE/orders/{id}") {
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
