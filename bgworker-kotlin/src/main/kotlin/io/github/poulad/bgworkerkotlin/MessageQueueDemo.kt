package io.github.poulad.bgworkerkotlin

import com.rabbitmq.client.ConnectionFactory
import com.viartemev.thewhiterabbit.channel.channel
import com.viartemev.thewhiterabbit.channel.consume
import io.github.poulad.sharedlib.config.getConfigurationItemOrDefault
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive

import org.slf4j.LoggerFactory
import kotlin.random.Random

private val logger = LoggerFactory.getLogger("RabbitMQDemo")
private const val CONSUMER_QUEUE_NAME = "myQueue"

suspend fun demoRabbitMQ() {
    val rabbitMQUri = getConfigurationItemOrDefault("PLD_RABBITMQ_URI", "beegee-worker.rabbitmq.uri")

    val connection = ConnectionFactory().apply {
        setUri(rabbitMQUri)
        useNio()
    }.newConnection("beegee-worker-${Char(Random.nextInt(26) + 97)}${Char(Random.nextInt(10) + 48)}")
        ?: throw Exception("Failed to create a connection")

    logger.debug("RabbitMQ connection \"${connection.clientProvidedName}\" is established")

    connection.channel {
        coroutineScope {
            val currentCoroutineScope = this
            consume(CONSUMER_QUEUE_NAME, 10) {
                while (currentCoroutineScope.isActive) {
                    consumeMessageWithConfirm {
                        println(it)
                        println(String(it.body))
                    }
                }
            }
        }
    }
}
