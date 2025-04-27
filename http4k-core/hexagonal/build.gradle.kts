import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20"
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("org.http4k.hexagonal.MarketKt")
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.property("kotlinVersion")}")
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation(platform("dev.forkhandles:forkhandles-bom:${project.property("forkHandlesVersion")}"))

    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-config")
    implementation("org.http4k:http4k-platform-core")
    implementation("dev.forkhandles:result4k")

    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation("org.http4k:http4k-testing-strikt")
    testImplementation("org.http4k:http4k-testing-webdriver")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${project.property("junitVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${project.property("junitVersion")}")
}