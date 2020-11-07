package com.example

import com.lightstep.opentelemetry.launcher.OpenTelemetryConfiguration.newBuilder
import io.opentelemetry.sdk.OpenTelemetrySdk
import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.events.AddOpenTelemetryTraces
import org.http4k.events.AutoJsonEvents
import org.http4k.events.Event
import org.http4k.events.EventFilters
import org.http4k.events.EventFilters.AddTimestamp
import org.http4k.events.then
import org.http4k.filter.ClientFilters
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.filter.OpenTelemetryMetrics
import org.http4k.filter.OpenTelemetryTracing
import org.http4k.filter.ResponseFilters.ReportHttpTransaction
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.time.Duration

data class IncomingTransaction(val uri: Uri, val latency: Duration) : Event

fun OpenTelemetryApp(proxyHttp: HttpHandler): HttpHandler {
    // the EventFilters will attach timestamp and trace information to the logs
    val events = AddTimestamp()
        .then(EventFilters.AddOpenTelemetryTraces())
        .then(AutoJsonEvents(Jackson))

    return ServerFilters.OpenTelemetryMetrics.RequestCounter()
        .then(ServerFilters.OpenTelemetryMetrics.RequestTimer())
        .then(ServerFilters.OpenTelemetryTracing())
        .then(ReportHttpTransaction {
            events(IncomingTransaction(it.request.uri, it.duration))
        })
        .then(
            routes("/trace" bind GET to {
                val body = proxyHttp(
                    Request(POST, "/repeat").body("hello world!")
                ).bodyString()

                Response(OK).body(body)
            })
        )
}


fun OpenTelemetryServer(
    port: Int,
    thirdPartyAppPort: Int,
    token: String,
    appName: String): Http4kServer {

    val proxyHttp =
        SetBaseUriFrom(Uri.of("http://localhost:$thirdPartyAppPort"))
            .then(ClientFilters.OpenTelemetryTracing())
            .then(JavaHttpClient())

    newBuilder()
        .setServiceName(appName)
        .setAccessToken(token)
        .install()

    Runtime.getRuntime().addShutdownHook(Thread {
        OpenTelemetrySdk.getTracerManagement().shutdown()
    })

    return OpenTelemetryApp(proxyHttp).asServer(SunHttp(port))
}
