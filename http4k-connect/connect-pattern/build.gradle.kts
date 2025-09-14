

dependencies {

    testImplementation("org.http4k:http4k-core:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-client-okhttp:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-server-netty:${project.property("http4kVersion")}")

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.http4k:http4k-testing-hamkrest:${project.property("http4kVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation(libs.mockk)
    testImplementation(libs.forkhandles.result4k)
    testRuntimeOnly(libs.junit.platform.launcher)
}
