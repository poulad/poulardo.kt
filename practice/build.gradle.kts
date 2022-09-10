import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "${rootProject.ext["kotlin_version"]}"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.21" // TODO: use kotlinVersion variable
    application
}

group = "io.github.poulad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val arrowVersion = "1.1.2"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation(project(":shared-lib-kotlin"))
    implementation("io.arrow-kt:arrow-core:$arrowVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("io.github.poulad.practice.Main")
}
