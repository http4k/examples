

plugins {
    application
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-format-jackson")

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass.set("com.example.JsonApiKt")
}
