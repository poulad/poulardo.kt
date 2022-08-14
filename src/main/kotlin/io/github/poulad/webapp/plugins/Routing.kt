package io.github.poulad.webapp.plugins

import io.github.poulad.webapp.routes.customerRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        customerRouting()
    }
}
