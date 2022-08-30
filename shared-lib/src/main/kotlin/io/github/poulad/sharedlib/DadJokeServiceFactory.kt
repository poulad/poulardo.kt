package io.github.poulad.sharedlib

class DadJokeServiceFactory {
    companion object {
        @JvmStatic
        fun newDadJokeService(): DadJokeService = DefaultDadJokeService()
    }
}
