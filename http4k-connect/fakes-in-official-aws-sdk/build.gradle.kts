import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    kotlin("jvm") version "2.0.21"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)
}

val gradleProperties = Properties().apply {
    load(rootProject.file("gradle.properties").inputStream())
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
    }

    named<KotlinCompile>("compileTestKotlin") {
        kotlinOptions.jvmTarget = "21"
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}


dependencies {
    implementation(platform("software.amazon.awssdk:bom:${gradleProperties["awsSdkVersion"]}"))
    testImplementation(platform("org.http4k:http4k-bom:${gradleProperties["http4kVersion"]}"))

    implementation("software.amazon.awssdk:dynamodb") // Use the official V2 AWS SDK in your production code
    implementation("software.amazon.awssdk:s3") // Use the official V2 AWS SDK in your production code

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-platform-aws") // Get the http4k adapter for the official V2 AWS SDK
    testImplementation("org.http4k:http4k-connect-amazon-dynamodb-fake") // Get the fake DynamoDB client
    testImplementation("org.http4k:http4k-connect-amazon-s3-fake") // Get the fake S3 client
    testImplementation("org.http4k:http4k-testing-kotest")

}
