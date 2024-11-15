plugins {
    kotlin("jvm") version "2.0.21"
    application
    id("org.graalvm.buildtools.native") version "0.9.28"
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }
}

repositories {
    mavenCentral()
    google()
}

graalvmNative {
    toolchainDetection.set(true)
    binaries {
        named("main") {
            imageName.set("helloworld")
            mainClass.set("com.example.HelloWorldKt")
            useFatJar.set(true)
        }
    }
}

val http4kVersion = providers.gradleProperty("http4kVersion").orNull
val kotlinVersion = "2.0.21"
val junitVersion = providers.gradleProperty("junitVersion").orNull

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation(platform("org.http4k:http4k-bom:${http4kVersion}"))
    implementation("org.http4k:http4k-core")

    testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
