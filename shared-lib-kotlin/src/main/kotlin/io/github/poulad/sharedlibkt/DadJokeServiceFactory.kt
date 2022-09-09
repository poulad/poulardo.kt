package io.github.poulad.sharedlibkt

class DadJokeServiceFactory {
    companion object {
        @JvmStatic
        fun newDadJokeService(): DadJokeService = DefaultDadJokeService()
    }
}
