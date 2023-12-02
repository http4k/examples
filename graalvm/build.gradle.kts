import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

buildscript {
    repositories {
        mavenCentral()
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

apply(plugin = "java")
apply(plugin = "kotlin")
apply(plugin = "com.github.johnrengelman.shadow")

repositories {
    mavenCentral()
}

val http4kVersion = providers.gradleProperty("http4kVersion").orNull
val kotlinVersion = providers.gradleProperty("kotlinVersion").orNull
val junitVersion = providers.gradleProperty("junitVersion").orNull

java {
    sourceCompatibility = VERSION_11
    targetCompatibility = VERSION_11
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            allWarningsAsErrors = true
        }
    }

    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("example")
        archiveClassifier = ""
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.example.HelloWorldKt"))
        }
    }

    named<Test>("test").configure {
        useJUnitPlatform {}
    }

}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation(platform("org.http4k:http4k-bom:${http4kVersion}"))
    implementation("org.http4k:http4k-core")

    testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
