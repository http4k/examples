dependencies {
    implementation(platform("${libs.http4k.bom.get()}:${project.property("http4kVersion")}"))
    implementation(libs.http4k.server.jetty)
    implementation(libs.http4k.ai.mcp.sdk)
    implementation(libs.http4k.serverless.lambda)
    implementation(libs.jsoup)
    testRuntimeOnly(libs.junit.platform.launcher)
}
