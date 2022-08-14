package io.github.poulad.webapp.routes

import io.github.poulad.webapp.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customers") {
        get {
            call.respond(customerStorage)
        }

        get("{id?}") {
            val id = this.call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val customer = customerStorage.find { it.id == id }
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(customer)
        }

        post { }
        delete("{id?}") { }
    }
}