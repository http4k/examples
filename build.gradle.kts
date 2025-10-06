import org.gradle.api.JavaVersion.VERSION_21
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.BIN
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.versions)
    alias(libs.plugins.typeflows)
}

allprojects {
    group = "org.http4k.examples"
    version = "1.0.0"

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    tasks.withType<Wrapper> {
        gradleVersion = "9.0.0"
        distributionType = BIN
    }

}

subprojects {
    apply(plugin = "kotlin")

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JVM_21)
            allWarningsAsErrors = false
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = VERSION_21.toString()
        targetCompatibility = VERSION_21.toString()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    typeflowsApi(libs.typeflows.github)
    typeflowsApi(libs.typeflows.github.marketplace)
    typeflowsApi(libs.http4k.standards)
}
