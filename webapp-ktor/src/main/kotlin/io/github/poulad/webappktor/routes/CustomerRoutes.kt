package io.github.poulad.webappktor.routes

import io.github.poulad.webappktor.cache.RedisRepository
import io.github.poulad.webappktor.dao.dao
import io.github.poulad.webappktor.models.Customer
import io.github.poulad.webappktor.models.customerStorage
import io.github.poulad.webappktor.queue.QueueProducerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("$BASE_API_ROUTE/customers") {
        get {
            val allCustomers = RedisRepository.new().loadAllCustomers()
                .sortedBy { it.id }

            call.respond(allCustomers)
        }

        get("{id}") {
            val id = this.call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val customer = RedisRepository.new().getCustomerById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            // TODO remove this
            QueueProducerService.new().demoRabbitMQ()

            call.respond(customer)
        }

        post {
            val customerDto = call.receive<Customer>()
            val customer = dao.addNewCustomer(customerDto.firstName, customerDto.lastName, customerDto.email)
                ?: return@post call.respond(HttpStatusCode.NotAcceptable)

            RedisRepository.new().addNewCustomer(customer)

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
