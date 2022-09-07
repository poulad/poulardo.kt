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

val coroutinesVersion = "1.6.4"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("io.github.crackthecodeabhi:kreds:0.8")
    implementation("com.viartemev:the-white-rabbit:0.0.6")
    implementation("ch.qos.logback:logback-classic:1.2.11")
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
    mainClass.set("io.github.poulad.beegeeworker.MainKt")
}
