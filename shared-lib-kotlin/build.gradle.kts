import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "${rootProject.ext["kotlin_version"]}"
val ktorClientVersion = "${rootProject.ext["ktor_client_version"]}"
val exposedOrmVersion = "${rootProject.ext["exposed_orm_version"]}"
val kotlinLoggingVersion = "${rootProject.ext["kotlin_logging_version"]}"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.21"
}

group = "io.github.poulad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorClientVersion")
    implementation("io.ktor:ktor-client-cio:$ktorClientVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorClientVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedOrmVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedOrmVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedOrmVersion")
    implementation("io.github.crackthecodeabhi:kreds:0.8")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
