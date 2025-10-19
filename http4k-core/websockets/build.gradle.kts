

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-platform-core")
    implementation("org.http4k:http4k-config")
    implementation("org.http4k:http4k-server-undertow")

    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-client-websocket")

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}
