package io.github.poulad.sharedlibkt.cache

internal enum class EntityCachePrefix(val prefix: String) {
    CUSTOMER("customer_"),
    ORDER("order_"),
    REPORT("report_"),
    ;

    fun getKey(entityId: String) = "$prefix$entityId"
}