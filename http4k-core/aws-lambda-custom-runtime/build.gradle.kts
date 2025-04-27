import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
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

    withType<ShadowJar> {
        manifest {
            attributes(mapOf("Main-Class" to "org.http4k.example.MyHttp4kFunctionKt"))
        }
        archiveBaseName.set("http4k-lambda")
        archiveClassifier.set("")
        archiveVersion.set("")
        mergeServiceFiles()
        minimize()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-serverless-lambda")
    implementation("org.http4k:http4k-serverless-lambda-runtime")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}