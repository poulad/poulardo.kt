package io.github.poulad.webappspring.service

import io.github.poulad.webappspring.model.Author
import org.springframework.data.repository.CrudRepository

interface AuthorRepository : CrudRepository<Author, Long> {

    fun findByLogin(login: String): Author?
}
