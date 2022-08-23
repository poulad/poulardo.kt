package io.github.poulad.webapp

import io.github.poulad.webapp.models.OrderTotalDto
import io.github.poulad.webapp.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {

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

    @Test
    fun shouldGetTotalOfOrder() = testApplication {
        val response = client.get("orders/2020-04-03-01/total")

        val orderTotalDto = Json.decodeFromString<OrderTotalDto>(response.bodyAsText())

        assertEquals(17.37, orderTotalDto.total)
    }
}
