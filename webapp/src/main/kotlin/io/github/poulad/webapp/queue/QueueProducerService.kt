package io.github.poulad.webapp.queue


import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.MessageProperties
import com.viartemev.thewhiterabbit.channel.confirmChannel
import com.viartemev.thewhiterabbit.channel.publish
import com.viartemev.thewhiterabbit.publisher.OutboundMessage
import io.github.poulad.sharedlib.config.getConfigurationItemOrDefault
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.slf4j.Logger
import kotlin.random.Random

class QueueProducerService(
    private val logger: Logger
) {

    suspend fun demoRabbitMQ() {
        val rabbitMQUri = getConfigurationItemOrDefault("PLD_RABBITMQ_URI", "webapp.rabbitmq.uri")

        val connection = ConnectionFactory().apply {
            setUri(rabbitMQUri)
            useNio()
        }.newConnection("webapp-${Char(Random.nextInt(26) + 97)}${Char(Random.nextInt(10) + 48)}")
            ?: throw Exception("Failed to create a connection")

        logger.debug("RabbitMQ connection \"${connection.clientProvidedName}\" is established")

        connection.confirmChannel {
            publish {
                coroutineScope {
                    val msg = OutboundMessage("", "myQueue", MessageProperties.PERSISTENT_TEXT_PLAIN, "Hello, World!")
                    val res = publishWithConfirmAsync(coroutineContext, listOf(msg)).awaitAll()
                    assert(res.all { it }) { "Didn't receive confirmation for all the published messages!" }
                    logger.info("a message was published to the RabbitMQ queue.")
                }
            }
        }
    }
}
