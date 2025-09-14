plugins {
    application
}

application {
    mainClass.set("merge_signals.BadApplesKt")
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-web-datastar")
    implementation("org.http4k:http4k-template-handlebars")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-format-moshi")
    implementation("dev.forkhandles:time4k:${project.property("forkHandlesVersion")}")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.junit.platform:junit-platform-launcher:1.13.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("com.natpryce:hamkrest:1.8.0.0")
}
