

dependencies {
    api(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))

    api("org.http4k:http4k-connect-core")

    // the adapter we are extending
    api("org.http4k:http4k-connect-github")

    api("org.http4k:http4k-format-moshi")

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.platform.launcher)
}
