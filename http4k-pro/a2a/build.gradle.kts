dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k.pro:http4k-ai-a2a-sdk")
    implementation("org.http4k.pro:http4k-ai-a2a-client")

    // only needed for reflection
    implementation("org.http4k:http4k-format-moshi")

    testImplementation("org.http4k:http4k-testing-hamkrest")
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
}
