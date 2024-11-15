import java.util.Properties

plugins {
    kotlin("jvm") version "1.9.23"
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

dependencies {
    implementation(platform("software.amazon.awssdk:bom:${gradleProperties["aws_sdk_version"]}"))
    testImplementation(platform("org.http4k:http4k-bom:${gradleProperties["http4k_version"]}"))
    testImplementation(platform("org.http4k:http4k-connect-bom:${gradleProperties["http4k_connect_version"]}"))

    implementation("software.amazon.awssdk:dynamodb") // Use the official V2 AWS SDK in your production code
    implementation("software.amazon.awssdk:s3") // Use the official V2 AWS SDK in your production code

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-aws") // Get the http4k adapter for the official V2 AWS SDK
    testImplementation("org.http4k:http4k-connect-amazon-dynamodb-fake") // Get the fake DynamoDB client
    testImplementation("org.http4k:http4k-connect-amazon-s3-fake") // Get the fake S3 client
    testImplementation("org.http4k:http4k-testing-kotest")

}
