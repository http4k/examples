

plugins {
    application
}

val arrowVersion = "1.1.2"

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation(platform("dev.forkhandles:forkhandles-bom:${project.property("forkHandlesVersion")}"))

    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-config")
    implementation("org.http4k:http4k-platform-core")
    implementation("io.arrow-kt:arrow-core:$arrowVersion")

    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation("org.http4k:http4k-testing-strikt")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.http4k:http4k-testing-webdriver")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${project.property("junitVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${project.property("junitVersion")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}

application {
    mainClass.set("org.http4k.hexagonal.MarketKt")
}
