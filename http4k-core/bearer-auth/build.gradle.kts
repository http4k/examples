repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "2.0.21"
    application
}

application {
    mainClass.set("com.example.BearerAuthKt")
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "21"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "21"
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.properties["http4kVersion"].toString()}"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.http4k:http4k-core")
    implementation("com.nimbusds:nimbus-jose-jwt:9.31")

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-testing-kotest")
}
