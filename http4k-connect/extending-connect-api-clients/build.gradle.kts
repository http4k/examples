import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    kotlin("jvm") version "1.9.23"
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
            jvmTarget = "11"
        }
    }

    named<KotlinCompile>("compileTestKotlin") {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

val gradleProperties = Properties().apply {
    load(rootProject.file("gradle.properties").inputStream())
}

dependencies {
    api(platform("org.http4k:http4k-connect-bom:${gradleProperties["http4k_connect_version"]}"))

    api("org.http4k:http4k-connect-core")

    // the adapter we are extending
    api("org.http4k:http4k-connect-github")

    api("org.http4k:http4k-format-moshi")

    testImplementation(platform("org.junit:junit-bom:${gradleProperties["junit_version"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("io.mockk:mockk:${gradleProperties["mockk_version"]}")
}
