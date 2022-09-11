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

val ktorServerVersion = "2.1.0"
val exposedOrmVersion = "${rootProject.ext["exposed_orm_version"]}"
val kotlinLoggingVersion = "${rootProject.ext["kotlin_logging_version"]}"


dependencies {
    implementation(project(":shared-lib-kotlin"))
    implementation(project(":shared-lib-java"))
    implementation("io.ktor:ktor-server-core:$ktorServerVersion")
    implementation("io.ktor:ktor-server-netty:$ktorServerVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorServerVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorServerVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorServerVersion")
    implementation("io.ktor:ktor-server-default-headers:$ktorServerVersion")
    implementation("io.ktor:ktor-server-cors:$ktorServerVersion")
    implementation("io.ktor:ktor-server-auth:$ktorServerVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorServerVersion")
    implementation("io.ktor:ktor-server-call-id:$ktorServerVersion")
    implementation("io.ktor:ktor-client-core:$ktorServerVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.jetbrains.exposed:exposed-core:$exposedOrmVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedOrmVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedOrmVersion")
    implementation("com.viartemev:the-white-rabbit:0.0.6")
    implementation("com.h2database:h2:2.1.214")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:$ktorServerVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorServerVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test>().configureEach {
    reports.html.required.set(false)
    reports.junitXml.required.set(false)
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
    mainClass.set("io.github.poulad.webappktor.ApplicationKt")
}
