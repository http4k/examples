

plugins {
    application
}

dependencies {
    api(platform("org.http4k:http4k-bom:${rootProject.property("http4kVersion")}"))
    api("org.http4k:http4k-core")

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass.set("org.http4k.examples.Http4kReactMainKt")
}
