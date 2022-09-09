package io.github.poulad.bgworkerkotlin

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    println("Hello, World!")
    launch {
        demoRedis()
        demoRabbitMQ()
    }
}
