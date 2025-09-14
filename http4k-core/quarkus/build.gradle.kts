

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.quarkus)
}

dependencies {
    implementation(enforcedPlatform(libs.quarkus.universe.bom))
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-bridge-servlet")

    implementation("io.quarkus:quarkus-kotlin")
    testImplementation("io.quarkus:quarkus-junit5")

    implementation(libs.quarkus.undertow)
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly(libs.junit.platform.launcher)
}
