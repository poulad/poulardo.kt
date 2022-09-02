package io.github.poulad.someapp.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SimpleController {

    val logger = LoggerFactory.getLogger(SimpleController::class.java)!!

    @GetMapping("/api/hello-world")
    fun getSimpleText(): ResponseEntity<String> {
        logger.debug("about to say hello world")
        return ResponseEntity.ok("Hello, World!")
    }

    @GetMapping("/api/hello-world2")
    fun getSimpleText2(): String {
        logger.debug("about to say hello world2")
        return "Hello, World! 2"
    }

}
