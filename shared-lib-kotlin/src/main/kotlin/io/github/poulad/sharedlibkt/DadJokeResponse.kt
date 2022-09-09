package io.github.poulad.sharedlibkt

import kotlinx.serialization.Serializable

@Serializable
data class DadJokeResponse(
    val id: String,
    val joke: String,
    val status: Int,
)
