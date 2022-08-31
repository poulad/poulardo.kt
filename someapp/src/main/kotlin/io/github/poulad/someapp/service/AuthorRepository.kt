package io.github.poulad.someapp.service

import io.github.poulad.someapp.model.Author
import org.springframework.data.repository.CrudRepository

interface AuthorRepository : CrudRepository<Author, Long> {

    fun findByLogin(login: String): Author?
}
