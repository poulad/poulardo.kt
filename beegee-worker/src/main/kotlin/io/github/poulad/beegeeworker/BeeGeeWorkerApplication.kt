package io.github.poulad.beegeeworker

import io.github.poulad.beegeeworker.service.MessageProducerService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*


@SpringBootApplication
open class BeeGeeWorkerApplication

fun main(args: Array<String>) {
    val context = runApplication<BeeGeeWorkerApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }

    val rabbitTemplate: RabbitTemplate
    try {
        rabbitTemplate = context.getBean(RabbitTemplate::class.java)
    } catch (t: Throwable) {
        println(t)
    }

    val messageProducerService = context.getBean(MessageProducerService::class.java)
    messageProducerService.produceMessage("hello, world at ${Date()}")
}
