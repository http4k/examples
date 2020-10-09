package com.example

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.time.Clock
import java.time.Instant
import kotlin.random.Random

data class JsonMessage(val now: Instant,
                       val randomNumber: Int,
                       val bool: Boolean)

fun JsonApi(clock: Clock, random: Random): HttpHandler {
    val bodyLens = Body.auto<JsonMessage>().toLens()

    return routes(
        "/echo" bind POST to {
            val received = bodyLens(it)
            Response(OK).with(bodyLens of received)
        },
        "/" bind GET to {
            Response(OK).with(bodyLens of JsonMessage(Instant.now(clock), random.nextInt(), true)) }
    )
}

fun main() {
    JsonApi(Clock.systemDefaultZone(), Random(0)).asServer(SunHttp(8080)).start()
}
