package io.github.poulad.sharedlibkt.config

/**
 * Gets an application configuration item from first environment variable and then a system [propertyName], if exists.
 * Otherwise, returns a [default] value.
 */
fun getConfigurationItemOrDefault(envVarName: String, propertyName: String = "", default: String = ""): String {
    return System.getenv(envVarName)
        ?: System.getProperty(propertyName)
        ?: default
}
