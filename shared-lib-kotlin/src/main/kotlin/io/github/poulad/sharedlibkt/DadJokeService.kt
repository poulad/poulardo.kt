package io.github.poulad.sharedlibkt

interface DadJokeService {
    suspend fun getJoke(): String
}
