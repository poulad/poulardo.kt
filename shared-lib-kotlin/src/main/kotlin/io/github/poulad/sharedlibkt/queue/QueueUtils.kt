package io.github.poulad.sharedlibkt.queue

import com.rabbitmq.client.ConnectionFactory
import io.github.poulad.sharedlibkt.config.getConfigurationItemOrDefault
import kotlin.random.Random

fun newConnectionFactory(): ConnectionFactory {
    val rabbitMQUri = getConfigurationItemOrDefault("PLD_RABBITMQ_URI", "poulardokt.rabbitmq.uri")
    if (rabbitMQUri.isEmpty()) {
        throw IllegalArgumentException("Failed to get RabbitMQ address URI!")
    }
    return ConnectionFactory().apply {
        setUri(rabbitMQUri)
        useNio()
    }
}

fun newRabbitMQConnectionName(serviceName: String = ""): String {
    val randomLowerChars = (0..2).map { Char(Random.nextInt(26) + 97) }
    val randomNumbers = (0..2).map { Char(Random.nextInt(10) + 48) }
    return (randomLowerChars + randomNumbers).shuffled()
        .joinToString(prefix = "${serviceName.ifEmpty { "svc" }}-", separator = "")
}
