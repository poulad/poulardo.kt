package io.github.poulad.sharedlibkt.cache

enum class PubSubChannel(val channelName: String) {
    CUSTOMERS("customers"),
    ORDER("orders")
    ;

    override fun toString() = channelName
}
