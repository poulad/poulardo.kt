package io.github.poulad.webapp.routes

import io.github.poulad.webapp.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.listOrderRoutes() {
    get("/orders") {
        if (orderStorage.isEmpty()) {
            call.respond(HttpStatusCode.NoContent)
            return@get
        }
        call.respond(orderStorage)
    }
}