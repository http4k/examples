

plugins {
    application
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.properties["http4kVersion"].toString()}"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.http4k:http4k-core")
    implementation(libs.nimbus.jose.jwt)

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-testing-kotest")
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass.set("com.example.BearerAuthKt")
}
