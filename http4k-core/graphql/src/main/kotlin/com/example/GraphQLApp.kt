package com.example

import com.example.http4k.schema.context.UserDbHandler
import com.example.http4k.schema.simple.BookDbHandler
import org.http4k.core.Filter
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.lens.RequestKey
import org.http4k.lens.RequestLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.graphQL
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

/**
 * This graphQL app does not require any context from the incoming request.
 */
private fun bookApp() = graphQL(BookDbHandler())

/**
 * This graphQL app does require context (eg. auth) from the incoming request.
 */
private fun userApp(): RoutingHttpHandler {
    fun AddUserAgentToContext(user: RequestLens<String>) = Filter { next ->
        {
            next(it.with(user of (it.header("User-Agent") ?: "unknown")))
        }
    }

    val user = RequestKey.required<String>("user")

    return AddUserAgentToContext(user)
        .then(graphQL(UserDbHandler(), user))
}

/**
 * Compose the applications together
 */
fun GraphQLApp() = routes(
    "/graphql" bind routes(
        "/user" bind userApp(),
        "/book" bind bookApp()
    )
)

fun main() {
    GraphQLApp().asServer(SunHttp(8000)).start()
}
