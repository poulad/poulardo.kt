plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
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
