package io.github.poulad.webapp.plugins

import io.github.poulad.webapp.routes.customerRouting
import io.github.poulad.webapp.routes.getOrderRoute
import io.github.poulad.webapp.routes.listOrderRoutes
import io.github.poulad.webapp.routes.totalizeOrderRoute
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrderRoutes()
        getOrderRoute()
        totalizeOrderRoute()

        static("/") {
            staticRootFolder = File("assets")
            files(".")
            default("index.html")
        }

        // TODO: if (developmentMode) trace { application.log.trace(it.buildText()) }
    }
}
