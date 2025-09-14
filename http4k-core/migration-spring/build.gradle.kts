

dependencies {
	implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
	implementation(libs.spring.boot.starter.web)
	implementation(libs.jackson.module.kotlin)
	implementation(libs.kotlin.reflect)

	implementation("org.http4k:http4k-bridge-spring")
	implementation("org.http4k:http4k-format-jackson")

	implementation(libs.spring.boot.starter.actuator)

	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.kotlin.test.junit5)
	testRuntimeOnly(libs.junit.platform.launcher)
}
