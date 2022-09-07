package io.github.poulad.beegeeworker

fun getConfigurationItem(envVarName: String, appPropertyName: String, default: String = ""): String {
    return System.getenv(envVarName)
        ?: System.getProperty(appPropertyName)
        ?: default
}
