

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-platform-core")
    implementation("org.http4k:http4k-config")
    implementation("org.http4k:http4k-server-undertow")

    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-client-websocket")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}
