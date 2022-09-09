package io.github.poulad.webappktor.cache

internal enum class EntityCachePrefix(val prefix: String) {
    CUSTOMER("customer_"),
    ORDER("order_"),
    REPORT("report_"),
    ;

    fun getKey(entityId: String) = "$prefix$entityId"
}
