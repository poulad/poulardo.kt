package io.github.poulad.sharedlibkt.queue

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.viartemev.thewhiterabbit.channel.channel
import com.viartemev.thewhiterabbit.channel.consume
import io.github.poulad.sharedlibjava.queue.QueueName
import io.github.poulad.sharedlibkt.cache.RedisRepository
import io.github.poulad.sharedlibkt.model.Customer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class DefaultRabbitMQConsumerService(
    private val connectionFactory: ConnectionFactory,
    private val redisRepository: RedisRepository
) : QueueConsumerService, AutoCloseable {

    private var _connection: Connection? = null

    private fun ensureConnectedToServer(): Connection {
        if (_connection == null) {
            // a network connection to the RabbitMQ server is established:
            val newConnection = connectionFactory.newConnection(newRabbitMQConnectionName("bgworker"))
                ?: throw Exception("Failed to create a connection")

            logger.debug { "RabbitMQ connection \"${newConnection.clientProvidedName}\" is established" }
            _connection = newConnection
        }
        return _connection!!
    }

    override suspend fun consumeQueue() {
        val connection = ensureConnectedToServer()
        connection.channel {
            coroutineScope {
                val currentCoroutineScope = this
                consume(QueueName.MY_QUEUE.queueName, 3) {
                    while (currentCoroutineScope.isActive) {
                        consumeMessageWithConfirm {
                            logger.trace { "RabbitMQ consumer received a delivery with tag ${it.envelope?.deliveryTag}" }
                            val bodyStr = String(it.body)
                            logger.debug { "RabbitMQ consumer received message: `$bodyStr`" }
                            try {
                                getCustomer(id = bodyStr)
                            } catch (t: Throwable) {
                                logger.error(t) { "Failed to connect to Redis for customer #$bodyStr!" }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getCustomer(id: String): Customer? {
        val customer = redisRepository.getCustomerById(id)
        logger.debug { "Found customer: $customer" }
        return customer
    }

    override fun close() {
        _connection?.close()
    }
}
