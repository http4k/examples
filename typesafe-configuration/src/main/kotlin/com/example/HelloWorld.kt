package com.example

import com.example.Settings.GITHUB_URI
import com.example.Settings.TIMEOUT
import org.http4k.config.Environment
import org.http4k.config.Environment.Companion.ENV
import org.http4k.config.EnvironmentKey
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.lens.duration
import org.http4k.lens.uri
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

object Settings {
    val GITHUB_URI = EnvironmentKey.uri().defaulted("URL", Uri.of("https://github.com"))
    val TIMEOUT = EnvironmentKey.duration().required("TIMEOUT")
}

fun HelloWorld(env: Environment): HttpHandler {
    val timeout = TIMEOUT(env)
    val github = GITHUB_URI(env)

    return routes("/" bind GET to { Response(OK)
        .body("Uri was configured with: $github, Timeout was configured with $timeout") })
}

fun main() {
    HelloWorld(ENV).asServer(SunHttp(8080)).start()
}
