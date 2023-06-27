import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("com.google.devtools.ksp") version "1.8.22-1.0.11"
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
apply(plugin = "com.google.devtools.ksp")

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
    implementation(platform("org.http4k:http4k-bom:${gradleProperties["http4kVersion"]}"))
    implementation(platform("org.http4k:http4k-connect-bom:${gradleProperties["http4kConnectVersion"]}"))
    implementation("org.http4k:http4k-core")

    ksp("org.http4k:http4k-connect-ksp-generator:${gradleProperties["http4kConnectVersion"]}")

    // dependencies from http4k-connect
    implementation("org.http4k:http4k-connect-amazon-s3")
    implementation("org.http4k:http4k-connect-amazon-kms")
    testImplementation("org.http4k:http4k-connect-amazon-s3-fake")
    testImplementation("org.http4k:http4k-connect-amazon-kms-fake")

    testImplementation(platform("org.junit:junit-bom:${gradleProperties["junitVersion"]}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.mockk:mockk:${gradleProperties["mockkVersion"]}")
}

