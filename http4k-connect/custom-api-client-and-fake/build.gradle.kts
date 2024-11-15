import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    kotlin("jvm") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.20"
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
    implementation(platform("org.http4k:http4k-connect-bom:${gradleProperties["http4k_connect_version"]}"))

    // this is required for implementing your own adapter
    implementation("org.http4k:http4k-connect-core")

    // needed by out custom adapter, but optional if you aren"t going to use it!
    implementation("org.http4k:http4k-format-moshi")

    // this and the plugin are only required for generating custom action extension functions (if you want to)
    ksp("org.http4k:http4k-connect-ksp-generator:${gradleProperties["http4k_connect_version"]}")

    testImplementation(platform("org.junit:junit-bom:${gradleProperties["junit_version"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("io.mockk:mockk:${gradleProperties["mockk_version"]}")

    // these are required for implementing your own fake
    testImplementation("org.http4k:http4k-connect-core-fake")
    testImplementation("org.http4k:http4k-connect-storage-core")
}
