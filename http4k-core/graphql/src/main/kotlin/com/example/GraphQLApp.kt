package com.example

import com.example.http4k.schema.context.UserDbHandler
import com.example.http4k.schema.simple.BookDbHandler
import org.http4k.core.Filter
import org.http4k.core.RequestContexts
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters.InitialiseRequestContext
import org.http4k.lens.RequestContextKey
import org.http4k.lens.RequestContextLens
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
    fun AddUserAgentToContext(user: RequestContextLens<String>) = Filter { next ->
        {
            next(it.with(user of (it.header("User-Agent") ?: "unknown")))
        }
    }

    val contexts = RequestContexts()
    val user = RequestContextKey.required<String>(contexts)

    return InitialiseRequestContext(contexts)
        .then(AddUserAgentToContext(user))
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
