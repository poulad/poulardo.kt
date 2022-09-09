plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
