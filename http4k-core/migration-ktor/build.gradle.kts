

plugins {
    application
}

val ktorVersion = "3.0.3"
val logbackVersion = "1.4.14"

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-config-yaml:$ktorVersion")
    implementation("org.http4k:http4k-bridge-ktor:${project.property("http4kVersion")}")

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${project.property("kotlinVersion")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
