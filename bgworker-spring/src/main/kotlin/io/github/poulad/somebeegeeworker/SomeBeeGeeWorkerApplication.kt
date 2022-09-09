package io.github.poulad.somebeegeeworker

import io.github.poulad.somebeegeeworker.service.MessageProducerService
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*


@SpringBootApplication
open class SomeBeeGeeWorkerApplication

fun main(args: Array<String>) {
    val context = runApplication<SomeBeeGeeWorkerApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }

    val messageProducerService = context.getBean(MessageProducerService::class.java)
    messageProducerService.produceMessage("hello, world at ${Date()}")
}
