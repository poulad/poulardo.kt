package coroutine

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<String>()
    val sendChannel = channel as SendChannel<String>
    val receiveChannel = channel as ReceiveChannel<String>

    val producerJob = launch {
        repeat(50) {
            delay(400) // busy computing
            println("--> Sending element $it over the channel")
            sendChannel.send("Number #$it")
        }

        delay(700)
        sendChannel.close()
    }

    val consumerJob = launch {
        repeat(51) {
            val result = receiveChannel.receiveCatching()
            if (result.isSuccess) {
                println("~~> Received: '${result.getOrThrow()}'")
            } else {
                println("There was an issue with the channel! $result")
            }
        }
    }

    producerJob.join()
    consumerJob.join()
}