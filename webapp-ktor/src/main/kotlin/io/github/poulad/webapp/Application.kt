package io.github.poulad.webapp

import io.github.poulad.webapp.dao.DatabaseFactory
import io.github.poulad.webapp.plugins.configureRouting
import io.github.poulad.webapp.plugins.configureSerialization
import io.github.poulad.webapp.routes.BASE_API_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import io.ktor.util.*
import kotlinx.coroutines.delay
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    // TODO install CORS plugin

    DatabaseFactory.init()
    configureRouting()
    configureSerialization()

    install(Authentication) {
        basic("auth-basic") {

            val passwordDigesterFunc = getDigestFunction("SHA-256") { "ktor${it.length}" }
            val hashedUserTable = UserHashedTableAuth(
                passwordDigesterFunc,
                mapOf(
                    "admin" to passwordDigesterFunc("password"),
                    "poulad" to passwordDigesterFunc("abc123"),
                )
            )

            realm = "Access to the admin routes"
            validate { credentials ->
                hashedUserTable.authenticate(credentials)
            }
        }
    }

    install(CallId) {
        retrieveFromHeader(HttpHeaders.XRequestId)
        generate(10, "abcdefghijklmnopqrstuvwxyz0123456789+/=-")
        replyToHeader(HttpHeaders.XRequestId)
    }

    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> call.request.path().startsWith(BASE_API_ROUTE) }
        callIdMdc()
    }
}
