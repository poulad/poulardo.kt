package io.github.poulad.sharedlib

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class DefaultDadJokeService : DadJokeService {

    override suspend fun getJoke(): String {
        val client = HttpClient(CIO)
        val httpResponse = client.get("https://icanhazdadjoke.com/") {
            accept(ContentType.parse("application/json"))
        }

        if (httpResponse.status != HttpStatusCode.OK) {
            throw RuntimeException("Request failed. $httpResponse")
        }

        val json = httpResponse.bodyAsText()
        val apiResponse = Json.decodeFromString<DadJokeResponse>(json)

        return apiResponse.joke
    }
}
