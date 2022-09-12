package io.github.poulad.sharedlibkt.queue

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.viartemev.thewhiterabbit.channel.channel
import com.viartemev.thewhiterabbit.channel.consume
import io.github.poulad.sharedlibjava.queue.QueueName
import io.github.poulad.sharedlibkt.cache.RedisRepository
import io.github.poulad.sharedlibkt.config.getConfigurationItemOrDefault
import io.github.poulad.sharedlibkt.model.Customer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import mu.KotlinLogging
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

class DefaultRabbitMQConsumerService(private val connection: Connection, private val redisRepository: RedisRepository) :
    QueueConsumerService {

    companion object {
        fun newConnection(): Connection {
            val rabbitMQUri = getConfigurationItemOrDefault("PLD_RABBITMQ_URI", "beegee-worker.rabbitmq.uri")

            val connection = ConnectionFactory().apply {
                setUri(rabbitMQUri)
                useNio()
            }.newConnection("bgworkerkt-${Char(Random.nextInt(26) + 97)}${Char(Random.nextInt(10) + 48)}")
                ?: throw Exception("Failed to create a connection")

            logger.debug { "RabbitMQ connection \"${connection.clientProvidedName}\" is established" }
            return connection
        }
    }

    override suspend fun consumeQueue() {
        connection.channel {
            coroutineScope {
                val currentCoroutineScope = this
                consume(QueueName.MY_QUEUE.queueName, 10) {
                    while (currentCoroutineScope.isActive) {
                        consumeMessageWithConfirm {
                            println(it)
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
}
