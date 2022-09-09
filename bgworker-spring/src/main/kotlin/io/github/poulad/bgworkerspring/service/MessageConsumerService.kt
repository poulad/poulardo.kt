package io.github.poulad.bgworkerspring.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MessageConsumerService {

    private val logger by lazy { LoggerFactory.getLogger(MessageConsumerService::class.java)!! }

    @Suppress("unused") // method is invoked by RabbitMQ client(SimpleMessageListenerContainer)
    fun consumeMessage(payload: String) = runBlocking { consume(payload) }

    private suspend fun consume(payload: String) {
        delay(100)
        logger.info("Received message payload: $payload")
    }

}
