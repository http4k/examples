plugins {
    application
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-web-datastar")
    implementation("org.http4k:http4k-template-handlebars")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-format-moshi")
    implementation(libs.forkhandles.time4k)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation(libs.hamkrest)
}

tasks.register<JavaExec>("gameOfLife") {
    group = "application"
    mainClass.set("gameoflife.MainKt")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("mergeFragments") {
    group = "application"
    mainClass.set("merge_fragments.MainKt")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("mergeSignals") {
    group = "application"
    mainClass.set("merge_signals.MainKt")
    classpath = sourceSets.main.get().runtimeClasspath
}
