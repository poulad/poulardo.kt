package io.github.poulad.sharedlibkt.cache

import io.github.crackthecodeabhi.kreds.args.SetOption
import io.github.crackthecodeabhi.kreds.connection.*
import io.github.crackthecodeabhi.kreds.protocol.KredsRedisDataException
import io.github.poulad.sharedlibkt.config.getConfigurationItemOrDefault
import io.github.poulad.sharedlibkt.model.Customer
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class DefaultRedisRepository private constructor(
    private val redisClient: KredsClient,
) : RedisRepository, AutoCloseable {

    companion object {
        @JvmStatic
        fun new(): RedisRepository {
            val host = getConfigurationItemOrDefault("PLD_REDIS_HOST", "poulardokt.redis.host")
            val port = getConfigurationItemOrDefault("PLD_REDIS_PORT", "poulardokt.redis.port")
            val redisClient = newClient(Endpoint.from("$host:$port"))
            return DefaultRedisRepository(redisClient)
        }
    }

    override suspend fun loadAllCustomers(): List<Customer> {
        ensureConnected()
        val allCustomersKeys = redisClient.keys("${EntityCachePrefix.CUSTOMER.prefix}*")
        val allCustomersList = mutableListOf<Customer>()
        for (key in allCustomersKeys) {
            val customerJson = redisClient.get(key)!! // TODO: key could be deleted meanwhile
            val customer = Json.decodeFromString<Customer>(customerJson)
            allCustomersList.add(customer)
        }
        return listOf(*allCustomersList.toTypedArray()) // returning an immutable list
    }

    override suspend fun getCustomerById(id: String): Customer? {
        ensureConnected()
        val customerJson = redisClient.get(EntityCachePrefix.CUSTOMER.getKey(id)) ?: return null

        logger.trace { "Retrieved a customer by ID: $customerJson" }

        return Json.decodeFromString<Customer>(customerJson)
    }

    override suspend fun addNewCustomer(customer: Customer) {
        ensureConnected()
        val customerJson = Json.encodeToString(customer)
        val previousValue = redisClient.set(
            EntityCachePrefix.CUSTOMER.getKey(customer.id), customerJson, SetOption.Builder(get = true).build()
        )
        if (previousValue != null) {
            logger.warn { "The record for Customer ${customer.id} was overwritten. Previous value was: $previousValue" }
        }
    }

    override suspend fun publishToCustomersChannel(message: String) {
        val host = getConfigurationItemOrDefault("PLD_REDIS_HOST", "poulardokt.redis.host")
        val port = getConfigurationItemOrDefault("PLD_REDIS_PORT", "poulardokt.redis.port")
        val user = getConfigurationItemOrDefault("PLD_REDIS_USER", "poulardokt.redis.user")
        val pass = getConfigurationItemOrDefault("PLD_REDIS_PASS", "poulardokt.redis.pass")

        newClient(Endpoint.from("$host:$port")).use { publisher ->
            publisher.auth(user, pass)
            for (i in 1 until 10) {
                val n = publisher.publish(PubSubChannel.CUSTOMERS.channelName, "message-$i")
                logger.debug { "Published message to channel and n=$n" }
            }
        }
    }

    override suspend fun subscribeToCustomersChannel() {
        // This call returns after all the coroutines finish.
        coroutineScope {
            val kredSubscriptionHandler = object : AbstractKredsSubscriber() {

                override fun onMessage(channel: String, message: String) {
                    println("Received message: $message from channel $channel")
                }

                override fun onSubscribe(channel: String, subscribedChannels: Long) {
                    println("Subscribed to channel: $channel")
                }

                override fun onUnsubscribe(channel: String, subscribedChannels: Long) {
                    println("Unsubscribed from channel: $channel")
                }

                override fun onException(ex: Throwable) {
                    println("Exception while handling subscription to redis: ${ex.stackTrace}")
                }

            }

            val host = getConfigurationItemOrDefault("PLD_REDIS_HOST", "poulardokt.redis.host")
            val port = getConfigurationItemOrDefault("PLD_REDIS_PORT", "poulardokt.redis.port")
            val user = getConfigurationItemOrDefault("PLD_REDIS_USER", "poulardokt.redis.user")
            val pass = getConfigurationItemOrDefault("PLD_REDIS_PASS", "poulardokt.redis.pass")

            val subscriberClient = newSubscriberClient(Endpoint.from("$host:$port"), kredSubscriptionHandler)
            subscriberClient.auth(user, pass)
            subscriberClient.subscribe(PubSubChannel.CUSTOMERS.channelName)
        }
    }

    private suspend fun ensureConnected(): Boolean {
        try {
            redisClient.serverVersion()
            return true // no issues in reading server's INFO. already connected.
        } catch (_: KredsRedisDataException) {
        }

        val user = getConfigurationItemOrDefault("PLD_REDIS_USER", "poulardokt.redis.user")
        val pass = getConfigurationItemOrDefault("PLD_REDIS_PASS", "poulardokt.redis.pass")

        redisClient.auth(user, pass)
        return false
    }

    override fun close() {
        redisClient.close()
    }
}
