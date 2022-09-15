package io.github.poulad.bgworkerkt

import com.rabbitmq.client.ConnectionFactory
import io.github.poulad.sharedlibkt.cache.DefaultRedisRepository
import io.github.poulad.sharedlibkt.cache.RedisRepository
import io.github.poulad.sharedlibkt.queue.DefaultRabbitMQConsumerService
import io.github.poulad.sharedlibkt.queue.QueueConsumerService
import io.github.poulad.sharedlibkt.queue.newConnectionFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        bindSingleton<RedisRepository> { DefaultRedisRepository.new() }

        bindSingleton<QueueConsumerService> { new(::DefaultRabbitMQConsumerService) }
        bindSingleton<ConnectionFactory> { new(::newConnectionFactory) }
    }

//    val redis: RedisRepository by kodein.instance()
//    launch(Dispatchers.IO) { redis.subscribeToCustomersChannel() }

    val q: QueueConsumerService by kodein.instance()
    launch(Dispatchers.IO) {
        q.consumeQueue()
        q.consumeQueue()
        q.consumeQueue()
        q.consumeQueue()
        q.consumeQueue()
    }

//    shutdown() // Shutdown the Kreds event loop on application shutdown.
}
