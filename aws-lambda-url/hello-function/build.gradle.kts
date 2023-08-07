dependencies {
    implementation(platform("org.http4k:http4k-bom:5.2.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-serverless-lambda:5.2.1.0")
}

tasks.register("buildLambdaZip", Zip::class) {
    from(tasks.compileKotlin)
    from(tasks.processResources)
    into("lib") {
        from(configurations.compileClasspath)
    }
}