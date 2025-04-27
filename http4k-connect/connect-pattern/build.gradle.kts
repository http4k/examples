import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20"
}

repositories {
    mavenCentral()
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_21.toString()
        targetCompatibility = JavaVersion.VERSION_21.toString()
    }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:${project.property("kotlinVersion")}")

    testImplementation("org.http4k:http4k-core:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-client-okhttp:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-server-netty:${project.property("http4kVersion")}")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest:${project.property("http4kVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.mockk:mockk:${project.property("mockkVersion")}")
    testImplementation("dev.forkhandles:result4k:2.2.0.0")
}