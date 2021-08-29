package org.http4k.example

import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV1LambdaFunction

val http4kApp = routes(
    "/echo/{message:.*}" bind GET to {
        Response(OK).body(
            it.path("message") ?: "(nothing to echo, use /echo/<message>)"
        )
    },
    "/tea" bind GET to { Response(I_M_A_TEAPOT) },
    "/" bind GET to { Response(OK).body("ok") }
)

@Suppress("unused")
class MyHttp4kFunction : ApiGatewayV1LambdaFunction(http4kApp)
