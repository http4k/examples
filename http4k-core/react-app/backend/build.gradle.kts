

plugins {
    application
}

dependencies {
    api(platform("org.http4k:http4k-bom:${rootProject.property("http4kVersion")}"))
    api("org.http4k:http4k-core")

    testImplementation(platform("org.junit:junit-bom:${rootProject.property("junitVersion")}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}

application {
    mainClass.set("org.http4k.examples.Http4kReactMainKt")
}
