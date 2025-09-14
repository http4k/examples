

plugins {
    application
}

val graphqlKotlinSchemaGeneratorVersion = "3.6.7"

dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-api-graphql")
    implementation("com.expediagroup:graphql-kotlin-schema-generator:$graphqlKotlinSchemaGeneratorVersion")

    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")
}

application {
    mainClass.set("com.example.GraphQLAppKt")
}
