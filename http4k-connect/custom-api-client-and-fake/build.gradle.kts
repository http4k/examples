plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))

    // this is required for implementing your own adapter
    implementation("org.http4k:http4k-connect-core")

    // needed by out custom adapter, but optional if you aren"t going to use it!
    implementation("org.http4k:http4k-format-moshi")

    // this and the plugin are only required for generating custom action extension functions (if you want to)
    ksp("org.http4k:http4k-connect-ksp-generator:${project.property("http4kVersion")}")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("io.mockk:mockk:1.10.5")

    // these are required for implementing your own fake
    testImplementation("org.http4k:http4k-connect-core-fake")
    testImplementation("org.http4k:http4k-connect-storage-core")
}
