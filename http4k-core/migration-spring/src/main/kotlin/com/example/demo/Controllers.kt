package com.example.demo

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.json
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SpringUsersController(private val repository: UserRepository) {
    @GetMapping("/users")
    fun list(): List<User> = repository.finaAll()
}

// Provides dependencies to http4k application
@Component
class Http4kUsersController(private val repository: UserRepository) : HttpHandler by http4kApp(repository)

// Provides routes so existing Spring controllers can be migrated to
private fun http4kApp(repository: UserRepository): HttpHandler =
    routes(
        "/users_v2" bind GET to { Response(OK).json(repository.finaAll()) }, // equivalent to "/users" in Spring
    )
