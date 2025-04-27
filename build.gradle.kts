import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20" apply false
    id("com.google.devtools.ksp") version "2.1.20-1.0.31" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("io.quarkus") version "3.2.0.Final" apply false
}

allprojects {
    group = "org.http4k.examples"
    version = "1.0.0"

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    tasks.withType<Wrapper> {
        gradleVersion = "8.13"
        distributionType = Wrapper.DistributionType.BIN
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            allWarningsAsErrors = false
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_21.toString()
        targetCompatibility = JavaVersion.VERSION_21.toString()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}