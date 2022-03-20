import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")

    implementation("io.ktor:ktor-server-core:1.5.4")
    implementation("io.ktor:ktor-server-netty:1.5.4")

    implementation("io.insert-koin:koin-core:3.0.2")

    implementation("org.slf4j:slf4j-simple:1.7.30")

    implementation("io.ktor:ktor-client-cio:1.5.4")
    implementation("io.ktor:ktor-client-logging:1.5.4")

    testImplementation("org.testcontainers:junit-jupiter:1.15.3")

    testImplementation("org.testcontainers:testcontainers:1.15.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
}

tasks.test {
    useJUnitPlatform()
}
