package io.github.poulad.someapp.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SimpleController {

    @GetMapping("/api/hello-world")
    fun getSimpleText(): String = "Hello, World!"

}
