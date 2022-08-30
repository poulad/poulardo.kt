package io.github.poulad.webapp.routes

import io.ktor.server.application.*
import io.ktor.util.pipeline.*

internal const val BASE_API_ROUTE = "/api"

/**
 * Gets the logger instance for this application call
 */
fun PipelineContext<*, ApplicationCall>.logger() = call.application.environment.log
