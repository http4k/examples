dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    testImplementation("org.http4k.pro:http4k-tools-hotreload")
}
