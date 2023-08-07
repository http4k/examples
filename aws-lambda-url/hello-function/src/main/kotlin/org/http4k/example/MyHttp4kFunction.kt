package org.http4k.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV2LambdaFunction

val http4kApp = routes(
    "/echo/{message:.*}" bind GET to {
        Response(OK).body(
            it.path("message") ?: "(nothing to echo, use /echo/<message>)"
        )
    },
    "/external" bind GET to {
        Response(OK).body(JavaHttpClient()(Request(GET, "http://httpbin.org/get")).bodyString())
    },
    "/" bind GET to { Response(OK).body("Hello from Lambda URL!").header("content-type", "text/html; charset=utf-8") }
)

@Suppress("unused")
class HelloServerlessHttp4k : ApiGatewayV2LambdaFunction(http4kApp)
