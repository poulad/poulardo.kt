@file:OptIn(ExperimentalCoroutinesApi::class)

package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<String>()
    val sendChannel = channel as SendChannel<String>

    val receiveChannel = produceElements(this, sendChannel)

    val consumerJob = launch {
        repeat(5) {
            val result = receiveChannel.receiveCatching()
            if (result.isSuccess) {
                println("~~> Received: '${result.getOrThrow()}'")
            } else {
                println("There was an issue with the channel! $result")
            }
        }
        channel.close()
    }

    consumerJob.join()
}

suspend fun produceElements(
    coroutineScope: CoroutineScope,
    sendChannel: SendChannel<String>
): ReceiveChannel<String> = coroutineScope.produce {
    var x = 1
    while (true) {
        println("--> Sender is computing element $x")
        delay(400) // busy computing
        sendChannel.send("Number #$x")
        x++
    }
}