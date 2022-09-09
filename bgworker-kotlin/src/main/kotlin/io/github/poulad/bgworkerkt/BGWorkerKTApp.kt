package io.github.poulad.bgworkerkt

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    println("Hello, World!")
    launch {
        demoRedis()
        demoRabbitMQ()
    }
}
