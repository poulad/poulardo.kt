package io.github.poulad.webappktor

import io.github.poulad.sharedlibkt.model.OrderTotalDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KtorWebAppTests {

    @Test
    fun `should return 404`() = testApplication {
        val response = client.post("/some-non-existing-page")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertTrue(response.bodyAsText().isEmpty())
    }

    @Test
    @Disabled("need to mock dependencies like Redis client")
    fun `should create a new customer`() = testApplication {
        val response = client.post("/api/customers") {
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
    fun `should get total of an order`() = testApplication {
        val response = client.get("api/orders/2020-04-03-01/total")

        val orderTotalDto = Json.decodeFromString<OrderTotalDto>(response.bodyAsText())

        assertEquals(17.37, orderTotalDto.total)
    }
}
