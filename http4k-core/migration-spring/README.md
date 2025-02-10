# Migration from Spring to http4k
   
This repository shows a simple example of how to migrate a Spring Boot application to http4k.

The main parts are:
1. The usage of the http4k-bridge-spring to act as a fallback HTTP handler. (see [Application.kt](src/main/kotlin/com/example/demo/Application.kt#L21))
2. The definition of equivalent routes in http4k (see [Controllers.kt](src/main/kotlin/com/example/demo/Controllers.kt#L26))

Using this incremental approach, each `@RestController` in the application can be converted to http4k while keeping the application working as a whole. Once all routes are migrated, the dependency on Spring can be removed and the application can be deployed as a standalone http4k application.

