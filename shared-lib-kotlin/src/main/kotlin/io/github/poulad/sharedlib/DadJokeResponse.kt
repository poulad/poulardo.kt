package io.github.poulad.sharedlib

import kotlinx.serialization.Serializable

@Serializable
data class DadJokeResponse(
    val id: String,
    val joke: String,
    val status: Int,
)
