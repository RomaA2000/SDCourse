import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm")
    id("com.palantir.docker") version "0.27.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:1.5.4")
    implementation("io.ktor:ktor-server-netty:1.5.4")

    implementation("org.slf4j:slf4j-simple:1.7.30")
}

docker {
    name = "stock"
    files("build/libs/server-1.0.jar")
}

val jar = task("jar", type = Jar::class) {
    archiveBaseName.set(project.name)
    manifest {
        attributes["Main-Class"] = "server.ServerKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(jar)
    }
    "docker" {
        dependsOn(jar)
    }
}
