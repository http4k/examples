import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "org.http4k.example"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "kotlin")
    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

apply(plugin = "application")

dependencies{
    implementation(project(":deployment"))
}

application {
    mainClass.set("DeployKt")
}

