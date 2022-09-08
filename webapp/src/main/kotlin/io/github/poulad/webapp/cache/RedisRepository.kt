package io.github.poulad.webapp.cache

import io.github.crackthecodeabhi.kreds.args.SetOption
import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient
import io.github.poulad.sharedlib.config.getConfigurationItemOrDefault
import io.github.poulad.webapp.models.Customer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RedisRepository private constructor(
    private val redisClient: KredsClient,
    private val logger: Logger
) : AutoCloseable {

    companion object {
        suspend fun new(): RedisRepository {
            val host = getConfigurationItemOrDefault("PLD_REDIS_HOST", "beegee-worker.redis.host")
            val port = getConfigurationItemOrDefault("PLD_REDIS_PORT", "beegee-worker.redis.port")
            val user = getConfigurationItemOrDefault("PLD_REDIS_USER", "beegee-worker.redis.user")
            val pass = getConfigurationItemOrDefault("PLD_REDIS_PASS", "beegee-worker.redis.pass")

            val redisClient = newClient(Endpoint.from("$host:$port")).apply {
                auth(user, pass)
            }
            val logger = LoggerFactory.getLogger(RedisRepository::class.java)!!
            return RedisRepository(redisClient, logger)
        }
    }

    suspend fun loadAllCustomers(): List<Customer> {
        val allCustomersKeys = redisClient.keys("${EntityCachePrefix.CUSTOMER.prefix}*")
        val allCustomersList = mutableListOf<Customer>()
        for (key in allCustomersKeys) {
            val customerJson = redisClient.get(key)!! // TODO: key could be deleted meanwhile
            val customer = Json.decodeFromString<Customer>(customerJson)
            allCustomersList.add(customer)
        }
        return listOf(*allCustomersList.toTypedArray()) // returning an immutable list
    }

    suspend fun addNewCustomer(customer: Customer) {
        val customerJson = Json.encodeToString(customer)
        val previousValue = redisClient.set(
            EntityCachePrefix.CUSTOMER.getKey(customer.id), customerJson, SetOption.Builder(get = true).build()
        )
        if (previousValue != null) {
            logger.warn("The record for Customer ${customer.id} was overwritten. Previous value was: $previousValue")
        }
    }

    override fun close() {
        redisClient.close()
    }
}
