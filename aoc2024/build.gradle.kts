plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":aoc"))

    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("aoc.juli27.aoc2024.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
