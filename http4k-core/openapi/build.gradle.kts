

plugins {
    application
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-api-openapi")
    implementation("org.http4k:http4k-format-jackson")

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass.set("com.example.OpenApiKt")
}
