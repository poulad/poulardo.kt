package io.github.poulad.webappspring

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringWebApp

fun main(args: Array<String>) {
    runApplication<SpringWebApp>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
