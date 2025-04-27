

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:${project.property("springBootVersion")}")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${project.property("jacksonVersion")}")
	implementation("org.jetbrains.kotlin:kotlin-reflect:${project.property("kotlinVersion")}")

	implementation("org.http4k:http4k-bridge-spring:${project.property("http4kVersion")}")
	implementation("org.http4k:http4k-format-jackson:${project.property("http4kVersion")}")

	implementation("org.springframework.boot:spring-boot-starter-actuator:${project.property("springBootVersion")}")

	testImplementation("org.springframework.boot:spring-boot-starter-test:${project.property("springBootVersion")}")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:${project.property("kotlinVersion")}")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.0")
}
