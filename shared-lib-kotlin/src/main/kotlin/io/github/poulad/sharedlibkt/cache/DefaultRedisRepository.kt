package io.github.poulad.sharedlibkt.cache

import io.github.crackthecodeabhi.kreds.args.SetOption
import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient
import io.github.poulad.sharedlibkt.config.getConfigurationItemOrDefault
import io.github.poulad.sharedlibkt.model.Customer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class DefaultRedisRepository private constructor(
    private val redisClient: KredsClient,
) : RedisRepository, AutoCloseable {

    companion object {
        suspend fun new(): RedisRepository {
            val host = getConfigurationItemOrDefault("PLD_REDIS_HOST", "webapp.redis.host")
            val port = getConfigurationItemOrDefault("PLD_REDIS_PORT", "webapp.redis.port")
            val user = getConfigurationItemOrDefault("PLD_REDIS_USER", "webapp.redis.user")
            val pass = getConfigurationItemOrDefault("PLD_REDIS_PASS", "webapp.redis.pass")

            val redisClient = newClient(Endpoint.from("$host:$port")).apply {
                auth(user, pass)
            }
            return DefaultRedisRepository(redisClient)
        }
    }

    override suspend fun loadAllCustomers(): List<Customer> {
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
        val customerJson = redisClient.get(EntityCachePrefix.CUSTOMER.getKey(id))
            ?: return null

        logger.trace { "Retrieved a customer by ID: $customerJson" }

        return Json.decodeFromString<Customer>(customerJson)
    }

    override suspend fun addNewCustomer(customer: Customer) {
        val customerJson = Json.encodeToString(customer)
        val previousValue = redisClient.set(
            EntityCachePrefix.CUSTOMER.getKey(customer.id), customerJson, SetOption.Builder(get = true).build()
        )
        if (previousValue != null) {
            logger.warn { "The record for Customer ${customer.id} was overwritten. Previous value was: $previousValue" }
        }
    }

    override fun close() {
        redisClient.close()
    }
}
