package io.github.poulad.bgworkerspring.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service


@Service
class MessageProducerService(private val rabbitTemplate: RabbitTemplate) {
    private val routingKey = "myRoutingKey.messages"

    fun produceMessage(message: String): String {
        rabbitTemplate.convertAndSend(
            rabbitTemplate.exchange, routingKey, message
        )
        return "Message($message) has been produced."
    }
}
