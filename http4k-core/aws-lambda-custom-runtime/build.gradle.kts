

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
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
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}
