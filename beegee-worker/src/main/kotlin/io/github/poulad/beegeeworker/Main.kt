package io.github.poulad.beegeeworker

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.newClient
import io.github.crackthecodeabhi.kreds.connection.shutdown
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    println("Hello, World!")
    showEnv()
    //   launch { demoRabbitMQ() }
//    launch {
//        demo()
//    }
}

fun showEnv() {
    val env = System.getenv()
    env.keys.filterNotNull()
        .map { "$it=${env[it] ?: ""}" }
        .sorted()
        .forEach(::println)
}


suspend fun demoRedis() {
    val host = System.getenv("beegee-worker.redis.host")
        ?: System.getProperty("beegee-worker.redis.host")
        ?: "localhost"
    val port = System.getenv("beegee-worker.redis.port")
        ?: System.getProperty("beegee-worker.redis.port")
        ?: "6379"
    val user = System.getenv("beegee-worker.redis.user")
        ?: System.getProperty("beegee-worker.redis.user")
        ?: "user"
    val pass = System.getenv("beegee-worker.redis.pass")
        ?: System.getProperty("beegee-worker.redis.pass")
        ?: ""

    newClient(Endpoint.from("$host:$port")).use { client ->
        client.auth(user, pass)

        client.set("foo", "100")
        println("incremented value of foo ${client.incr("foo")}") // prints 101
        client.expire("foo", 3u) // set expiration to 3 seconds
        delay(3000)
        assert(client.get("foo") == null)

        shutdown()
    }
}
