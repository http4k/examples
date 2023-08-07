dependencies {
    implementation(platform("org.http4k:http4k-bom:${project.properties["http4kVersion"]}"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-serverless-lambda")
}

tasks.register("buildLambdaZip", Zip::class) {
    from(tasks.compileKotlin)
    from(tasks.processResources)
    into("lib") {
        from(configurations.compileClasspath)
    }
}
