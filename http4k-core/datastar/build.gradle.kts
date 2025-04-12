import org.gradle.api.JavaVersion.VERSION_21

plugins {
    kotlin("jvm") version "2.0.21"
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "21"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "21"
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    apply(plugin = "java")
    apply(plugin = "kotlin")

    dependencies {
        implementation(platform("org.http4k:http4k-bom:${project.properties["http4kVersion"]}"))
        implementation("org.http4k:http4k-web-datastar")
        implementation("org.http4k:http4k-template-handlebars")
        implementation("org.http4k:http4k-server-jetty")
        implementation("org.http4k:http4k-server-undertow")
        implementation("org.http4k:http4k-format-moshi")
        implementation("org.http4k:http4k-server-helidon")
        implementation("dev.forkhandles:time4k:2.22.2.1")

        testImplementation(platform("org.junit:junit-bom:${project.properties["junitVersion"]}"))
        testImplementation("org.junit.platform:junit-platform-launcher")
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.junit.jupiter:junit-jupiter-engine")
        testImplementation("org.http4k:http4k-testing-hamkrest")
        testImplementation("com.natpryce:hamkrest:_")
    }
}
