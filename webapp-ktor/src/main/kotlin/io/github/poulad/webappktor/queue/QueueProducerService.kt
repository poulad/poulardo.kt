package io.github.poulad.webappktor.queue


import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.MessageProperties
import com.viartemev.thewhiterabbit.channel.confirmChannel
import com.viartemev.thewhiterabbit.channel.publish
import com.viartemev.thewhiterabbit.publisher.OutboundMessage
import io.github.poulad.sharedlibjava.queue.QueueName
import io.github.poulad.sharedlibkt.config.getConfigurationItemOrDefault
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.random.Random

class QueueProducerService private constructor(
    private var connection: Connection,
    private val logger: Logger,
) : AutoCloseable {

    companion object {
        fun new(): QueueProducerService {
            val rabbitMQUri = getConfigurationItemOrDefault("PLD_RABBITMQ_URI", "webapp.rabbitmq.uri")

            val connection = ConnectionFactory().apply {
                setUri(rabbitMQUri)
                useNio()
            }.newConnection("webapp-${Char(Random.nextInt(26) + 97)}${Char(Random.nextInt(10) + 48)}")
                ?: throw Exception("Failed to create a connection")

            val logger = LoggerFactory.getLogger(QueueProducerService::class.java)!!
            return QueueProducerService(connection, logger)
        }
    }

    suspend fun ensureConnected(): Unit {
        TODO()
    }

    suspend fun publishMessage(message: String): Unit {
        connection.confirmChannel {
            use {
                publish {
                    coroutineScope {
                        val msg = OutboundMessage(
                            "",
                            QueueName.MY_QUEUE.queueName,
                            MessageProperties.PERSISTENT_TEXT_PLAIN,
                            message
                        )
                        val isPublishedSuccessfully = publishWithConfirm(msg)
                        if (!isPublishedSuccessfully) {
                            throw Exception("Message was not published successfully to the queue")
                        }
                        logger.debug("Published message: `$message`")
                    }
                }
            }
        }
    }

    suspend fun demoRabbitMQ() {
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

    override fun close() {
        connection.close()
    }
}
