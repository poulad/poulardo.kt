package io.github.poulad.sharedlibkt.queue

interface QueueConsumerService {
    suspend fun consumeQueue()
}
