package io.github.poulad.webappspring.controller

import io.github.poulad.webappspring.model.Author
import io.github.poulad.webappspring.model.AuthorCreationDto
import io.github.poulad.webappspring.service.AuthorRepository
import kotlinx.coroutines.delay
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URI

@Controller
@RequestMapping("api/authors")
class AuthorController(
    private val authorRepository: AuthorRepository
) {

    @GetMapping
    fun getAuthors(): ResponseEntity<List<Author>> {
        val allAuthorsList = authorRepository.findAll().map { it as Author }.toList()
        return ResponseEntity.ok(allAuthorsList)
    }

    @GetMapping("{login}")
    suspend fun getAuthorByLogin(@PathVariable login: String): ResponseEntity<Author> {
        delay(100)
        val author = authorRepository.findByLogin(login)
        return if (author != null) {
            ResponseEntity.ok(author)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    suspend fun createAuthor(@RequestBody(required = true) dto: AuthorCreationDto): ResponseEntity<Author> {
        val authorModel = Author(null, dto.login, dto.firstName, dto.lastName)
        authorRepository.save(authorModel)
        return ResponseEntity.created(URI.create("/api/authors/${dto.login}")).body(authorModel)
    }
}
