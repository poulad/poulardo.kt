package io.github.poulad.webapp.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*


fun Route.getStatsRoute() {
    authenticate("auth-basic") {
        get("$BASE_API_ROUTE/admin/stats") {
            val user = call.principal<UserIdPrincipal>()
                ?: return@get call.respond(HttpStatusCode.Unauthorized)

            return@get call.respond("Stats generated for ${user.name} at ${Date()}")
        }
    }
}
