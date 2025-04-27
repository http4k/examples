package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.lens.RequestKey
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.time.Clock

fun BearerAuthApi(clock: Clock): HttpHandler {
    val issuer = "http4k.org"
    val audience = listOf("http4k.org")
    val secretKey = "qwertyuiopasdfghjklzxcvbnm123456".toByteArray()

    val verifier = createHs256JwtAuthorizer(issuer = issuer, audience = audience, clock = clock, secretKey = secretKey)
    val signer =
        createHs256JwtSigner(issuer = issuer, audience = listOf("http4k.org"), clock = clock, secretKey = secretKey)

    val authLens = RequestKey.required<User>("user")

    // verify access token and generate a custom response
    val protectedApi = ServerFilters.BearerAuth(authLens, verifier).then(
        "/hello" bind GET to { request ->
            val user = authLens(request)
            Response(OK).body("Hello ${user.name}")
        }
    )

    // issue an access token (unprotected; DO NOT USE IN PRODUCTION)
    val issueAccessToken = "token/{user_id}" bind GET to { request ->
        val userId = request.path("user_id")!!
        val jwt = signer(User(userId, userId))
        Response(OK).body(jwt)
    }

    return routes(issueAccessToken, protectedApi)
}

fun main() {
    BearerAuthApi(Clock.systemUTC())
        .asServer(SunHttp(8080))
        .start()
        .also { println("Server started on http://localhost:${it.port()}") }
        .block()
}
