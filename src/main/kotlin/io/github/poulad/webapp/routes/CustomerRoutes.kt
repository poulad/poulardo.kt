package io.github.poulad.webapp.routes

import io.github.poulad.webapp.models.Customer
import io.github.poulad.webapp.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("$BASE_API_ROUTE_PREFIX/customers") {
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

        post {
            val customerDto = call.receive<Customer>()
            customerStorage.add(customerDto)
            call.respond(HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            if (customerStorage.removeIf { it.id == id }) {
                call.respond(HttpStatusCode.Accepted)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
