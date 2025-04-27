import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

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

    register<Zip>("buildZip") {
        dependsOn("compileKotlin", "processResources")
        from("${buildDir}/classes/kotlin/main")
        from("${buildDir}/resources/main")
        into("lib") {
            from(configurations.compileClasspath)
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-serverless-lambda")
    implementation("com.amazonaws:aws-lambda-java-events:3.10.0")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}