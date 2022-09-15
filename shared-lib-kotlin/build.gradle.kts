import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "${rootProject.ext["kotlin_version"]}"
val ktorClientVersion = "${rootProject.ext["ktor_client_version"]}"
val exposedOrmVersion = "${rootProject.ext["exposed_orm_version"]}"
val kotlinLoggingVersion = "${rootProject.ext["kotlin_logging_version"]}"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.21"
    id("io.gitlab.arturbosch.detekt").version("1.21.0")
}

group = "io.github.poulad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter() // used for "com.viartemev:the-white-rabbit" package.
}

dependencies {
    implementation(project(":shared-lib-java"))
    implementation(project(":deps:kreds"))
    implementation("io.ktor:ktor-client-core:$ktorClientVersion")
    implementation("io.ktor:ktor-client-cio:$ktorClientVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorClientVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedOrmVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedOrmVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedOrmVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("com.viartemev:the-white-rabbit:0.0.6")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
