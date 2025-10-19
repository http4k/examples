

plugins {
    application
    alias(libs.plugins.shadow)
    alias(libs.plugins.ksp)
    alias(libs.plugins.micronaut.application)
}

micronaut {
    version(libs.versions.micronaut.platform.get())
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    ksp(libs.micronaut.http.validation)
    ksp(libs.micronaut.serde.processor)
    
    implementation(libs.micronaut.kotlin.runtime)
    implementation(libs.micronaut.serde.jackson)
    implementation(libs.kotlin.reflect)
    implementation("org.http4k:http4k-bridge-micronaut")
    implementation("org.http4k:http4k-format-jackson")
    implementation(libs.micronaut.runtime)

    compileOnly(libs.micronaut.http.client)
    runtimeOnly(libs.logback.classic)
    runtimeOnly(libs.jackson.module.kotlin)
    testImplementation(libs.micronaut.http.client)
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass = "com.example.ApplicationKt"
}
