# Migration from Ktor to http4k

This repository shows a simple example of how to migrate a Ktor application to http4k.

This is enabled by adding the `KtorToHttp4kApplicationPlugin` to the application (see [Application.kt](src/main/kotlin/Application.kt#L16))

Using this incremental approach, each Ktor route in the application can be converted to http4k while keeping the application working as a whole. 

Once all routes are migrated, the dependency on Spring can be removed and the application can be deployed as a standalone http4k application.
