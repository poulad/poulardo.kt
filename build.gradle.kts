// avoiding loading Kotlin Gradle plugin multiple times in different subprojects.
// see https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
plugins {
    kotlin("jvm") version "1.6.21" apply false
}

allprojects {
    ext {
        this["kotlin_version"] = "1.6.21"
        this["kotlinx_coroutine_version"] = "1.6.4"
        this["ktor_client_version"] = "2.1.0"
        this["exposed_orm_version"] = "0.39.2"
    }
}
