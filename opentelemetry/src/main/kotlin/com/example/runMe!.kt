package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request

fun main() {
    val mainPort = 8080
    val thirdPartyPort = 9090

    val server = OpenTelemetryServer(
        mainPort,
        thirdPartyPort,
        System.getenv("LIGHTSTEP_TOKEN"),
        System.getenv("APP_NAME")
    )
    val thirdParty = ThirdPartyServer(thirdPartyPort).start()

    server.start().use {
        // make some requests
        repeat(5) {
            val client = JavaHttpClient()
            println(
                client(
                    Request(GET, "http://localhost:$mainPort/trace")).header("x-b3-traceid")
            )
        }
    }

    thirdParty.stop()
}


