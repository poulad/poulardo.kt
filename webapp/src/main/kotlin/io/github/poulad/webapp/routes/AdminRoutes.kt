package io.github.poulad.webapp.routes

import io.github.poulad.sharedlib.DadJokeServiceFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import java.util.*


@OptIn(ExperimentalCoroutinesApi::class)
fun Route.getStatsRoute() {
    authenticate("auth-basic") {
        get("$BASE_API_ROUTE/admin/stats") {
            val user = call.principal<UserIdPrincipal>()
                ?: return@get call.respond(HttpStatusCode.Unauthorized)

            logger().info("Generating stats for administrator user \"${user.name}\"")

            val statsDeferred = withTimeoutOrNull(5_000) {
                async {
                    delay(Random(System.currentTimeMillis()).nextLong(7_000))
                    "Stats generated for ${user.name} at ${Date()}"
                }
            }

            // TODO: DI?
            val dadJokeService = DadJokeServiceFactory.newDadJokeService()
            val jokeDeferred = withTimeoutOrNull(2_000) {
                async { dadJokeService.getJoke() }
            }

            awaitAll(
                *arrayOf(statsDeferred, jokeDeferred)
                    .filterNotNull()
                    .toTypedArray()
            )

            val statsText = statsDeferred?.getCompleted() ?: "STATS_TIMED_OUT"
            val joke = jokeDeferred?.getCompleted() ?: "JOKE_TIMED_OUT"

            logger().info("Here's the joke: $joke")

            return@get call.respond("$statsText\n\n$joke")
        }
    }
}
