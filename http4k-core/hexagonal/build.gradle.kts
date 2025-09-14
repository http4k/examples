

plugins {
    application
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation(platform("dev.forkhandles:forkhandles-bom:${project.property("forkHandlesVersion")}"))

    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-config")
    implementation("org.http4k:http4k-platform-core")
    implementation(libs.forkhandles.result4k)

    testImplementation("org.http4k:http4k-testing-chaos")
    testImplementation("org.http4k:http4k-testing-strikt")
    testImplementation("org.http4k:http4k-testing-webdriver")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.3")
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass.set("org.http4k.hexagonal.MarketKt")
}
