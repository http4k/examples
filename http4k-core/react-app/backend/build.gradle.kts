

plugins {
    application
}

dependencies {
    api(platform("org.http4k:http4k-bom:${rootProject.property("http4kVersion")}"))
    api("org.http4k:http4k-core")

    testImplementation(platform("org.junit:junit-bom:${rootProject.property("junitVersion")}"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}

application {
    mainClass.set("org.http4k.examples.Http4kReactMainKt")
}
