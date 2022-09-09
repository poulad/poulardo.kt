package io.github.poulad.bgworkerspring.config

import io.github.poulad.bgworkerspring.service.MessageConsumerService
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class RabbitMQConsumerConfig {

    private val queueName = "myQueue"

    @Bean
    open fun simpleMessageListenerContainer(
        connectionFactory: ConnectionFactory,
        messageListenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer {
        val simpleMessageListenerContainer = SimpleMessageListenerContainer()
        simpleMessageListenerContainer.connectionFactory = connectionFactory
        simpleMessageListenerContainer.setQueueNames(queueName)
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter)
        return simpleMessageListenerContainer
    }

    @Bean
    open fun messageListenerAdapter(consumeMessageService: MessageConsumerService): MessageListenerAdapter {
        return MessageListenerAdapter(consumeMessageService, "consumeMessage")
    }
}
