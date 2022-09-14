package io.github.poulad.bgworkerkt

import com.rabbitmq.client.ConnectionFactory
import io.github.poulad.sharedlibkt.cache.DefaultRedisRepository
import io.github.poulad.sharedlibkt.cache.RedisRepository
import io.github.poulad.sharedlibkt.queue.DefaultRabbitMQConsumerService
import io.github.poulad.sharedlibkt.queue.QueueConsumerService
import io.github.poulad.sharedlibkt.queue.newConnectionFactory
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.new

private val logger = KotlinLogging.logger { }

fun main(): Unit = runBlocking {
    logger.info { "Background Worker is starting..." }

    val kodein = DI {
        // TODO: avoid "runBlocking" here
        bindSingleton<RedisRepository> { runBlocking { DefaultRedisRepository.new() } }

        bindSingleton<QueueConsumerService> { new(::DefaultRabbitMQConsumerService) }
        bindSingleton<ConnectionFactory> { new(::newConnectionFactory) }
    }

    val q: QueueConsumerService by kodein.instance()
    q.consumeQueue()
}
