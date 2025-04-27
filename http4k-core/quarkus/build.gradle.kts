

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.quarkus") version "3.2.0.Final"
}

val quarkusVersion = "3.2.0.Final"

dependencies {
    implementation(enforcedPlatform("io.quarkus:quarkus-universe-bom:$quarkusVersion"))
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-bridge-servlet")

    implementation("io.quarkus:quarkus-kotlin")
    testImplementation("io.quarkus:quarkus-junit5")

    implementation("io.quarkus:quarkus-undertow:$quarkusVersion")
    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
