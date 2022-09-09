plugins {
    kotlin("multiplatform")
    application
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
}

val kotlinxHtmlVersion = "0.8.0"

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:$kotlinxHtmlVersion")
//    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
//    implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinxHtmlVersion")
}

// See https://kotlinlang.org/docs/multiplatform-dsl-reference.html#native-targets
kotlin {
    wasm32("native") {
        binaries {
            executable()
        }
    }
}

//tasks.withType<Wrapper> {
//    gradleVersion = "6.7.1"
//    distributionType = Wrapper.DistributionType.BIN
//}
