@file:OptIn(ExperimentalCoroutinesApi::class)

package io.github.poulad.practice.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main(args: Array<String>) = runBlocking<Unit> {
    val channel = Channel<String>(capacity = Channel.BUFFERED);
    val sendChannel = channel as SendChannel<String>
    val receiveChannel = this.produceElements(sendChannel)

    val consumerJob = launch {
//        receiveChannel.consumeEach {
//            println("Received \"$it\"")
//        }
        println("Receiver is ready to consume messages")
        for (msg in receiveChannel) {
            println("Consumer got element '$msg'")
        }
    }

    consumerJob.join()
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun CoroutineScope.produceElements(sendChannel: SendChannel<String>): ReceiveChannel<String> =
    this.produce {
        this.launch {
            repeat(15) {
                println("Computing the next number")
//                delay(100)
                while (sendChannel.isClosedForSend) {
                    delay(200)
                }
                println("Sending $it")
                sendChannel.send("Number $it")
            }
        }
    }
