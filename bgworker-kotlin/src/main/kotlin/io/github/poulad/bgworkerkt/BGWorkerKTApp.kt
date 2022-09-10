package io.github.poulad.bgworkerkt

import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("BGWorkerKtApp")!!

fun main(): Unit = runBlocking {
    logger.info("Background Worker is starting...")
    demoRabbitMQ()
}
