

dependencies {
    api(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))

    api("org.http4k:http4k-connect-core")

    // the adapter we are extending
    api("org.http4k:http4k-connect-github")

    api("org.http4k:http4k-format-moshi")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("io.mockk:mockk:1.10.5")
}
