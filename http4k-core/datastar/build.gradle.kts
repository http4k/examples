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
    implementation(libs.forkhandles.time4k)

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation(libs.junit.platform.launcher)
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation(libs.hamkrest)
}
