package io.github.poulad.practice.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.pow

fun main(args: Array<String>): Unit = runBlocking {
    val flow = startFlowing()
    launch { startCollectingValues(flow) }
    launch { startCollectingValues(flow, "x") }
}

fun startFlowing() = flow<String> {
    println("Emitting values from thread ${Thread.currentThread().name}")
    (1..20)
        .filter { it % 3 == 0 }
        .map { Pair(it, it.toDouble().pow(2.0).toInt()) }
        .map { "${it.first} squared is ${it.second}" }
        .forEach { emit(it) }
}

suspend fun startCollectingValues(strsFlow: Flow<String>, collectorId: String = ""): Unit {
    val id = "COLLECTOR[${collectorId.ifEmpty { "?" }}]"
    strsFlow.collect {
        println("$id -> $it")
    }
}
