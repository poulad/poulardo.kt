package io.github.poulad.someapp.controller

import io.github.poulad.someapp.model.Author
import io.github.poulad.someapp.model.AuthorCreationDto
import io.github.poulad.someapp.service.AuthorRepository
import kotlinx.coroutines.delay
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("api/authors")
class AuthorController(
    private val authorRepository: AuthorRepository
) {

    @GetMapping
    suspend fun getAuthors(): List<Author> {
        delay(400)
        return authorRepository.findAll()
            .map { it as Author }
            .toList()
    }

    @GetMapping("{login}")
    suspend fun getAuthorByLogin(@PathVariable login: String): Author {
        delay(100)
        val author = authorRepository.findByLogin(login)
        return author ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    suspend fun createAuthor(@RequestBody(required = true) dto: AuthorCreationDto): Author {
        val authorModel = Author(null, dto.login, dto.firstName, dto.lastName)
        authorRepository.save(authorModel)
        return authorModel
    }
}
