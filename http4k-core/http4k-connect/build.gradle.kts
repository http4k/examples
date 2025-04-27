

plugins {
    id("com.google.devtools.ksp")
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

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.mockk:mockk:1.10.5")
}
