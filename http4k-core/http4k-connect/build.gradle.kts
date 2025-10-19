plugins {
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")

    ksp("org.http4k:http4k-connect-ksp-generator:${project.property("http4kVersion")}")

    // dependencies from http4k-connect
    implementation("org.http4k:http4k-connect-amazon-s3")
    implementation("org.http4k:http4k-connect-amazon-kms")
    testImplementation("org.http4k:http4k-connect-amazon-s3-fake")
    testImplementation("org.http4k:http4k-connect-amazon-kms-fake")

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.platform.launcher)
}
