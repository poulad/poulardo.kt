package io.github.poulad.webapp

import io.github.poulad.webapp.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun shouldReturn404() = testApplication {
        val response = client.post("/some-non-existing-page")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertTrue(response.bodyAsText().isEmpty())
    }

    @Test
    fun shouldCreateNewCustomer() = testApplication {
        val response = client.post("/customers") {
            setBody(
                """
                {
                    "id": "100",
                    "firstName": "Joe",
                    "lastName": "Doe",
                    "email": "jdoe@email.com"
                }
            """.trimIndent()
            )
            contentType(ContentType.parse("application/json"))
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }
}
