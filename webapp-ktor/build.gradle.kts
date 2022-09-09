import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.21"
    application
    distribution
}

group = "io.github.poulad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    @Suppress("DEPRECATION") jcenter() // used for "com.viartemev:the-white-rabbit" package.
}

val ktorVersion = "2.1.0"
val exposedVersion = "0.39.2"

dependencies {
    implementation(project(":shared-lib-kotlin"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-call-id:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("io.github.crackthecodeabhi:kreds:0.8")
    implementation("com.viartemev:the-white-rabbit:0.0.6")
    implementation("com.h2database:h2:2.1.214")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

// NOTE: If not deploying this app to Heroku, comment out this stage to save build time.
// see https://ktor.io/docs/heroku.html#stage
//tasks {
//    create("stage").dependsOn("installDist")
//}

// Distribution plugin makes a single binary to run the app at "./webapp/build/install/webapp/bin/webapp".
// See https://docs.gradle.org/current/userguide/distribution_plugin.html
tasks.build {
    finalizedBy("installDist")
}

application {
    mainClass.set("io.github.poulad.webappktor.KtorWebAppKt")
}
