package io.github.poulad.bgworkerkotlin

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.newClient
import io.github.crackthecodeabhi.kreds.connection.shutdown
import io.github.poulad.sharedlib.config.getConfigurationItemOrDefault
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("RedisDemo")

suspend fun demoRedis() {
    val host = getConfigurationItemOrDefault("PLD_REDIS_HOST", "beegee-worker.redis.host")
    val port = getConfigurationItemOrDefault("PLD_REDIS_PORT", "beegee-worker.redis.port")
    val user = getConfigurationItemOrDefault("PLD_REDIS_USER", "beegee-worker.redis.user")
    val pass = getConfigurationItemOrDefault("PLD_REDIS_PASS", "beegee-worker.redis.pass")

    newClient(Endpoint.from("$host:$port")).use { client ->
        client.auth(user, pass)

        client.set("foo", "100")
        logger.info("incremented value of foo ${client.incr("foo")}") // prints 101
        client.expire("foo", 3u) // set expiration to 3 seconds
        delay(3000)
        assert(client.get("foo") == null)

        shutdown()
    }
}
