import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    distribution
}

group = "io.github.poulad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter() // used for "com.viartemev:the-white-rabbit" package.
}

val kotlinCoroutineVersion = "${rootProject.ext["kotlinx_coroutine_version"]}"
val kotlinLoggingVersion = "${rootProject.ext["kotlin_logging_version"]}"

dependencies {
    implementation(project(":shared-lib-kotlin"))
    implementation(project(":shared-lib-java"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutineVersion")
    implementation("io.github.crackthecodeabhi:kreds:0.8")
    implementation("com.viartemev:the-white-rabbit:0.0.6")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("org.kodein.di:kodein-di:7.14.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Test>().configureEach {
    reports.html.required.set(false)
    reports.junitXml.required.set(false)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.build {
    finalizedBy("installDist")
}

application {
    mainClass.set("io.github.poulad.bgworkerkt.BGWorkerKTAppKt")
}
