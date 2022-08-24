package io.github.poulad.webapp

import io.github.poulad.webapp.dao.DatabaseFactory
import io.github.poulad.webapp.plugins.configureRouting
import io.github.poulad.webapp.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    // TODO install CallLogging plugin. https://ktor.io/docs/call-logging.html
    // TODO install CORS plugin

    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
}
