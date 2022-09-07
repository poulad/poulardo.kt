package io.github.poulad.beegeeworker

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    println("Hello, World!")
    launch {
        demoRedis()
        demoRabbitMQ()
    }
}
