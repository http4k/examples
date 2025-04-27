

plugins {
    application
    id("com.github.johnrengelman.shadow")
    id("com.google.devtools.ksp")
    id("io.micronaut.application") version "4.0.3"
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation:${project.property("micronautVersion")}")
    ksp("io.micronaut.serde:micronaut-serde-processor:${project.property("micronautSerdeVersion")}")
    
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:${project.property("micronautKotlinVersion")}")
    implementation("io.micronaut.serde:micronaut-serde-jackson:${project.property("micronautSerdeVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${project.property("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.property("kotlinVersion")}")
    implementation("org.http4k:http4k-bridge-micronaut:${project.property("http4kVersion")}")
    implementation("org.http4k:http4k-format-jackson:${project.property("http4kVersion")}")
    implementation("io.micronaut:micronaut-runtime:${project.property("micronautVersion")}")

    compileOnly("io.micronaut:micronaut-http-client:${project.property("micronautVersion")}")
    runtimeOnly("ch.qos.logback:logback-classic:${project.property("logbackVersion")}")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:${project.property("jacksonVersion")}")
    testImplementation("io.micronaut:micronaut-http-client:${project.property("micronautVersion")}")
}

application {
    mainClass = "com.example.ApplicationKt"
}
