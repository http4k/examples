package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.filter.OpenTelemetryMetrics
import org.http4k.filter.OpenTelemetryTracing
import org.http4k.filter.ResponseFilters
import org.http4k.filter.ServerFilters
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun ProxyApp(thirdPartyHttp: HttpHandler) =
    ServerFilters.OpenTelemetryMetrics.RequestCounter()
        .then(ServerFilters.OpenTelemetryMetrics.RequestTimer())
        .then(ServerFilters.OpenTelemetryTracing())
        .then(ResponseFilters.ReportHttpTransaction {
            println("PROXYING TOOK ${it.duration.toMillis()}ms")
        })
        .then(ClientFilters.OpenTelemetryTracing())
        .then(thirdPartyHttp)

fun ProxyServer(serverPort: Int, thirdPartyPort: Int): Http4kServer {
    val thirdPartyHttp =
        SetBaseUriFrom(Uri.of("http://localhost:$thirdPartyPort"))
            .then(ClientFilters.OpenTelemetryTracing())
            .then(JavaHttpClient())

    return ProxyApp(thirdPartyHttp).asServer(SunHttp((serverPort)))
}
