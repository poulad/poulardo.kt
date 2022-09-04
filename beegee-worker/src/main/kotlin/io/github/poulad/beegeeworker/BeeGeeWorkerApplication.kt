package io.github.poulad.beegeeworker

import io.github.poulad.beegeeworker.service.MessageProducerService
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

    val messageProducerService = context.getBean(MessageProducerService::class.java)
    messageProducerService.produceMessage("hello, world at ${Date()}")
}
