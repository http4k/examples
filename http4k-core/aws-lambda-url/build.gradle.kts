

plugins {
    application
}

dependencies {
    testImplementation(kotlin("test"))
    testRuntimeOnly(libs.junit.platform.launcher)
}

application {
    mainClass.set("DeployKt")
}
