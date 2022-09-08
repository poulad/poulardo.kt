package io.github.poulad.webapp.routes

import io.github.poulad.webapp.cache.RedisRepository
import io.github.poulad.webapp.dao.dao
import io.github.poulad.webapp.models.Customer
import io.github.poulad.webapp.models.customerStorage
import io.github.poulad.webapp.queue.QueueProducerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

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
            QueueProducerService(LoggerFactory.getLogger(QueueProducerService::class.java)).demoRabbitMQ()

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
