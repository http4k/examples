

plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.google.devtools.ksp") version "2.2.20-2.0.3"
    id("io.micronaut.application") version "4.0.3"
}

val micronautVersion = "4.0.0"
val micronautKotlinVersion = "4.0.0"
val micronautSerdeVersion = "2.5.0"
val logbackVersion = "1.4.11"
val jacksonVersion = "2.15.2"

micronaut {
    version(micronautVersion)
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation:$micronautVersion")
    ksp("io.micronaut.serde:micronaut-serde-processor:$micronautSerdeVersion")
    
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:$micronautKotlinVersion")
    implementation("io.micronaut.serde:micronaut-serde-jackson:$micronautSerdeVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${project.property("kotlinVersion")}")
    implementation("org.http4k:http4k-bridge-micronaut:${project.property("http4kVersion")}")
    implementation("org.http4k:http4k-format-jackson:${project.property("http4kVersion")}")
    implementation("io.micronaut:micronaut-runtime:$micronautVersion")

    compileOnly("io.micronaut:micronaut-http-client:$micronautVersion")
    runtimeOnly("ch.qos.logback:logback-classic:$logbackVersion")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    testImplementation("io.micronaut:micronaut-http-client:$micronautVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}

application {
    mainClass = "com.example.ApplicationKt"
}
