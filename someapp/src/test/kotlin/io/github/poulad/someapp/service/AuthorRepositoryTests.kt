package io.github.poulad.someapp.service

import io.github.poulad.someapp.model.Author
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager


@DataJpaTest
class AuthorRepositoryTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val authorRepository: AuthorRepository,
) {

    @AfterEach
    fun cleanUp() {
        entityManager.flush()
    }

    @Test
    fun `Should query all authors`() {
        // ARRANGE:
        val juergen = Author(null, "springjuergen", "Juergen", "Hoeller")
        entityManager.persist(juergen)

        // ACT:
        val allAuthors = authorRepository.findAll()
            .map { it as Author }
            .toList()

        // ASSERT:
        assertEquals(1, allAuthors.size)
        val author1 = allAuthors[0]
        assertEquals(juergen, author1)

        // Cleanup
        entityManager.remove(author1)
    }

    @Test
    fun `Should find an author by their login`() {
        val author1 = Author(null, "bestseller", "John", "Smith")
        entityManager.persist(author1)

        val author2 = authorRepository.findByLogin("bestseller")!!

        assertEquals("bestseller", author2.login)
        assertEquals("John", author2.firstname)
        assertEquals("Smith", author2.lastname)

        // Cleanup
        entityManager.remove(author1)
    }
}
