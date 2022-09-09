package io.github.poulad.sharedlib

interface DadJokeService {
    suspend fun getJoke(): String
}
