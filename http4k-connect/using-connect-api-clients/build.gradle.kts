

dependencies {
    implementation(platform("${libs.http4k.bom.get()}:${project.property("http4kVersion")}"))

    implementation(libs.http4k.connect.amazon.kms)
    testImplementation(libs.http4k.connect.amazon.kms.fake)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.http4k.testing.hamkrest)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}
