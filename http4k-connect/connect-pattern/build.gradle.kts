

dependencies {

    testImplementation("org.http4k:http4k-core:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-client-okhttp:${project.property("http4kVersion")}")
    testImplementation("org.http4k:http4k-server-netty:${project.property("http4kVersion")}")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest:${project.property("http4kVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.mockk:mockk:1.10.5")
    testImplementation("dev.forkhandles:result4k:2.22.4.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}
