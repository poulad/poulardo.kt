package io.github.poulad.beegeeworker

import com.rabbitmq.client.ConnectionFactory
import com.viartemev.thewhiterabbit.channel.channel
import com.viartemev.thewhiterabbit.channel.consume
import org.slf4j.LoggerFactory
import kotlin.random.Random

private val logger = LoggerFactory.getLogger("RabbitMQDemo")
private const val CONSUMER_QUEUE_NAME = "myQueue"

suspend fun demoRabbitMQ() {
    val rabbitMqUri = System.getenv("PLD_RABBITMQ_URI")
        ?: System.getProperty("beegee-worker.rabbitmq.uri")
        ?: throw Exception("RabbitMQ URI is not found")

    val connection = ConnectionFactory().apply {
        setUri(rabbitMqUri)
        useNio()
    }.newConnection("beegee-worker-${Char(Random.nextInt(26) + 97)}${Char(Random.nextInt(10) + 48)}")
        ?: throw Exception("Failed to create a connection")

    logger.debug("RabbitMQ connection \"${connection.clientProvidedName}\" is established")

    connection.channel {
        consume(CONSUMER_QUEUE_NAME, 10) {
            while (true) {
                consumeMessageWithConfirm {
                    println(it)
                    println(String(it.body))
                }
            }
        }
    }
}
