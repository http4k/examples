import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
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
        kotlinOptions.jvmTarget = "21"
    }

    named<KotlinCompile>("compileTestKotlin") {
        kotlinOptions.jvmTarget = "21"
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
    implementation(platform("org.http4k:http4k-bom:${gradleProperties["http4kVersion"]}"))
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

