

val springBootVersion = "3.2.3"
val jacksonVersion = "2.15.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect:${project.property("kotlinVersion")}")

	implementation("org.http4k:http4k-bridge-spring:${project.property("http4kVersion")}")
	implementation("org.http4k:http4k-format-jackson:${project.property("http4kVersion")}")

	implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:${project.property("kotlinVersion")}")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}
