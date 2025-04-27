

dependencies {
    implementation(platform("software.amazon.awssdk:bom:${project.property("awsSdkVersion")}"))
    testImplementation(platform("org.http4k:http4k-bom:${project.property("http4kVersion")}"))

    implementation("software.amazon.awssdk:dynamodb") // Use the official V2 AWS SDK in your production code
    implementation("software.amazon.awssdk:s3") // Use the official V2 AWS SDK in your production code

    testImplementation(kotlin("test"))
    testImplementation("org.http4k:http4k-platform-aws") // Get the http4k adapter for the official V2 AWS SDK
    testImplementation("org.http4k:http4k-connect-amazon-dynamodb-fake") // Get the fake DynamoDB client
    testImplementation("org.http4k:http4k-connect-amazon-s3-fake") // Get the fake S3 client
    testImplementation("org.http4k:http4k-testing-kotest")
}
