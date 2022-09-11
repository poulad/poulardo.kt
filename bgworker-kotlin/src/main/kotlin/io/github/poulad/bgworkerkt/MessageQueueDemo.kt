package io.github.poulad.bgworkerkt

import com.rabbitmq.client.ConnectionFactory
import com.viartemev.thewhiterabbit.channel.channel
import com.viartemev.thewhiterabbit.channel.consume
import io.github.poulad.sharedlibjava.queue.QueueName
import io.github.poulad.sharedlibkt.cache.DefaultRedisRepository
import io.github.poulad.sharedlibkt.config.getConfigurationItemOrDefault
import io.github.poulad.sharedlibkt.model.Customer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import mu.KotlinLogging
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

suspend fun demoRabbitMQ() {
    val rabbitMQUri = getConfigurationItemOrDefault("PLD_RABBITMQ_URI", "beegee-worker.rabbitmq.uri")

    val connection = ConnectionFactory().apply {
        setUri(rabbitMQUri)
        useNio()
    }.newConnection("bgworkerkt-${Char(Random.nextInt(26) + 97)}${Char(Random.nextInt(10) + 48)}")
        ?: throw Exception("Failed to create a connection")

    logger.debug { "RabbitMQ connection \"${connection.clientProvidedName}\" is established" }

    connection.channel {
        coroutineScope {
            val currentCoroutineScope = this
            consume(QueueName.MY_QUEUE.queueName, 10) {
                while (currentCoroutineScope.isActive) {
                    consumeMessageWithConfirm {
                        println(it)
                        val bodyStr = String(it.body)
                        logger.debug { "Received message: `$bodyStr`" }
                        getCustomer(id = bodyStr)
                    }
                }
            }
        }
    }
}

private suspend fun getCustomer(id: String): Customer? {
    val customer = DefaultRedisRepository.new().getCustomerById(id)
    logger.debug { "Found customer: $customer" }
    return customer
}
