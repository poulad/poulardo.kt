package io.github.poulad.someapp.controller

import io.github.poulad.someapp.model.Author
import kotlinx.coroutines.delay
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthorController {

    @GetMapping("api/authors")
    suspend fun getAuthors(): List<Author> {
        delay(400)
        return emptyList()
    }
}
