// avoiding loading Kotlin Gradle plugin multiple times in different subprojects.
// see https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
plugins {
    kotlin("jvm") version "1.6.21" apply false
}

ext["kotlinVersion"] = "1.6.21"
