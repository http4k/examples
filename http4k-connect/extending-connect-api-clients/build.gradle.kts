import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    kotlin("jvm") version "2.0.21"
}
buildscript {
    repositories {
        mavenCentral()
    }

}
repositories {
    mavenCentral()
}

apply(plugin = "kotlin")

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "21"
        }
    }

    named<KotlinCompile>("compileTestKotlin") {
        kotlinOptions {
            jvmTarget = "21"
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

val gradleProperties = Properties().apply {
    load(rootProject.file("gradle.properties").inputStream())
}

dependencies {
    api(platform("org.http4k:http4k-bom:${gradleProperties["http4kVersion"]}"))

    api("org.http4k:http4k-connect-core")

    // the adapter we are extending
    api("org.http4k:http4k-connect-github")

    api("org.http4k:http4k-format-moshi")

    testImplementation(platform("org.junit:junit-bom:${gradleProperties["junitVersion"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("io.mockk:mockk:${gradleProperties["mockkVersion"]}")
}
