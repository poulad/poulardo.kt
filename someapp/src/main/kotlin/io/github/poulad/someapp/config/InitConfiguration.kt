package io.github.poulad.someapp.config

import io.github.poulad.someapp.model.Author
import io.github.poulad.someapp.service.AuthorRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InitConfiguration {

    val logger = LoggerFactory.getLogger(InitConfiguration::class.java)!!

    @Bean
    fun seedDatabaseSomeData(authorRepository: AuthorRepository) = ApplicationRunner {
        val isDbEmpty = authorRepository.count() == 0L
        if (!isDbEmpty) {
            logger.debug("There are Author entities in the database. Skipping seeding test data...")
            return@ApplicationRunner
        }

        val juergen = Author(null, "springjuergen", "Juergen", "Hoeller")
        val john = Author(null, "bestseller", "John", "Smith")
        authorRepository.saveAll(listOf(juergen, john))

        logger.debug("Seeding test data is completed successfully.")
    }
}
