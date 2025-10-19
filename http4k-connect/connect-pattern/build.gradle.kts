

dependencies {

    testImplementation("org.http4k:http4k-core:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-client-okhttp:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-server-netty:${project.property("http4kVersion")}")

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.http4k:http4k-testing-hamkrest:${project.property("http4kVersion")}")
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
    testImplementation(libs.forkhandles.result4k)
    testRuntimeOnly(libs.junit.platform.launcher)
}
