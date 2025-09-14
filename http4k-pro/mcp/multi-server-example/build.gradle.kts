dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k.pro:http4k-ai-mcp-sdk")
    testRuntimeOnly(libs.junit.platform.launcher)
}
