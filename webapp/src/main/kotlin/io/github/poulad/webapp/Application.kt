package io.github.poulad.webapp

import io.github.poulad.webapp.dao.DatabaseFactory
import io.github.poulad.webapp.plugins.configureRouting
import io.github.poulad.webapp.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.util.*
import kotlinx.coroutines.delay

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    // TODO install CallLogging plugin. https://ktor.io/docs/call-logging.html
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
}

private suspend fun validateBasicAuth(credential: UserPasswordCredential): Principal? {
    delay(1)
    return UserIdPrincipal("admin")
}
