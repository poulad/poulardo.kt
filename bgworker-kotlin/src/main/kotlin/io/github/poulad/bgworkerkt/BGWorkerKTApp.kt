package io.github.poulad.bgworkerkt

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

fun main(): Unit = runBlocking {
    logger.info { "Background Worker is starting..." }
    demoRabbitMQ()
}
