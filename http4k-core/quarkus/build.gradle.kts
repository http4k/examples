

plugins {
    id("com.github.johnrengelman.shadow")
    id("io.quarkus")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.property("kotlinVersion")}")
    implementation(enforcedPlatform("io.quarkus:quarkus-universe-bom:${project.property("quarkus_version")}"))
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-bridge-servlet")

    implementation("io.quarkus:quarkus-kotlin")
    testImplementation("io.quarkus:quarkus-junit5")

    implementation("io.quarkus:quarkus-undertow:${project.property("quarkus_version")}")
    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
