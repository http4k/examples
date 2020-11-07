package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request

fun main() {
    val mainPort = 8080
    val proxyPort = 9090
    val thirdPartyPort = 10010

    val server = OpenTelemetryServer(
        mainPort,
        proxyPort,
        System.getenv("LIGHTSTEP_TOKEN"),
        System.getenv("APP_NAME")
    )
    val proxy = ProxyServer(proxyPort, thirdPartyPort).start()
    val thirdParty = ThirdPartyServer(thirdPartyPort).start()

    server.start().use {
        // make some requests
        repeat(5) {
            val client = JavaHttpClient()
            println(
                client(
                    Request(GET, "http://localhost:$mainPort/opentelemetry")).header("x-b3-traceid")
            )
        }
    }

    proxy.stop()
    thirdParty.stop()
}


