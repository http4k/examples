import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kotlin("jvm") version "2.1.20" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "kotlin")

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            allWarningsAsErrors = false
        }
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }

    configurations {
        create("tests")
    }

    tasks.withType<Jar> {
        manifest {
            attributes(
                mapOf("release_version" to (project.version ?: "unknown"))
            )
        }
    }
}

tasks.named<KotlinCompile>("compileTestKotlin") {
    kotlinOptions {
        jvmTarget = "11"
    }
}