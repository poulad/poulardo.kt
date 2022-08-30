package io.github.poulad.webapp.routes

import io.github.poulad.webapp.dao.dao
import io.github.poulad.webapp.models.Customer
import io.github.poulad.webapp.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("$BASE_API_ROUTE/customers") {
        get {
            call.respond(dao.allCustomers())
        }

        get("{id}") {
            val id = this.call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val customer = customerStorage.find { it.id == id }
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(customer)
        }

        post {
            val customerDto = call.receive<Customer>()
            val customer = dao.addNewCustomer(customerDto.firstName, customerDto.lastName, customerDto.email)
                ?: return@post call.respond(HttpStatusCode.NotAcceptable)

            return@post call.respond(HttpStatusCode.Created, customer)
        }

        delete("{id}") {
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
