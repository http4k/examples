dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k.pro:http4k-ai-mcp-sdk")
    implementation("org.http4k:http4k-serverless-lambda")
    implementation("org.jsoup:jsoup:1.19.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}
