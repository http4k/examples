dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))

    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-platform-core")
    implementation("org.http4k:http4k-config")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k.pro:http4k-ai-mcp-sdk")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${project.property("kotlinVersion")}")


    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")

}
