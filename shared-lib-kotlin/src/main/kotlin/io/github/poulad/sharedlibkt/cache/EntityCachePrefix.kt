package io.github.poulad.sharedlibkt.cache

internal enum class EntityCachePrefix(val prefix: String) {
    CUSTOMER("customer"),
    ORDER("order"),
    REPORT("report"),
    ;

    fun getKey(entityId: String) = "${prefix}_${entityId}"
}
