

plugins {
    application
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.properties["http4kVersion"].toString()}"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.http4k:http4k-core")
    implementation("com.nimbusds:nimbus-jose-jwt:9.31")

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-testing-kotest")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}

application {
    mainClass.set("com.example.BearerAuthKt")
}
