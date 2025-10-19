dependencies {
    // for the anthropic version
    implementation("org.http4k:http4k-ai-llm-anthropic:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-connect-ai-anthropic-fake:${project.property("http4kVersion")}")

    // for the openai version
    implementation("org.http4k:http4k-ai-llm-openai:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-connect-ai-openai-fake:${project.property("http4kVersion")}")

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.http4k:http4k-testing-hamkrest:${project.property("http4kVersion")}")
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}
