package io.github.poulad.webappktor.plugins

import io.github.poulad.webappktor.routes.*
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
        getStatsRoute()

        static("/") {
            staticRootFolder = File("assets")
            files(".")
            default("index.html")
        }
    }
}
