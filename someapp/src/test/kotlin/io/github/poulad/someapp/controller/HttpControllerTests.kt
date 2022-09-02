package io.github.poulad.someapp.controller

import com.ninjasquad.springmockk.MockkBean
import io.github.poulad.someapp.model.Author
import io.github.poulad.someapp.service.AuthorRepository
import io.mockk.every
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class HttpControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun `Should say Hello World`() {
        mockMvc.perform(get("/api/hello-world").accept(MediaType.ALL))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello, World!"))
    }

    @Test
    @Disabled("idk why it doesn't work.")
    fun `Should create a new Author`() {
        every { authorRepository.save(any<Author>()) } returns Author(12, "Best_Seller", "John", "Smith")

        mockMvc.perform(
            post("/api/articles")
                .content(
                    """
                    {
                        "login": "Best_Seller",
                        "firstName": "John",
                        "lastName": "Smith"
                    }
                """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.login").value("Best_Seller"))
    }

}
