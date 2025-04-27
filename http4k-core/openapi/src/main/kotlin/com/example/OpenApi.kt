package com.example

import org.http4k.contract.ContractRoute
import org.http4k.contract.contract
import org.http4k.contract.meta
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.core.Body
import org.http4k.core.Credentials
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson
import org.http4k.format.Jackson.auto
import org.http4k.lens.Header
import org.http4k.lens.string
import org.http4k.security.BasicAuthSecurity
import org.http4k.security.NoSecurity
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.time.Clock
import java.time.Instant
import kotlin.random.Random

fun OpenApi(clock: Clock, random: Random): HttpHandler =
    veryUnsafeCorsFilter()
        .then(
            contract {
                renderer = OpenApi3(
                    ApiInfo(
                        "http4k Open API Example", "1.0", "" +
                            "<p>Credentials are <b>http4k/http4k</b></p>" +
                            "<a href='https://http4k.org/openapi3/?url=http%3A%2F%2Flocalhost:8080'>view this API in OpenAPI</a>"
                    ), Jackson
                )
                security = BasicAuthSecurity("", Credentials("http4k", "http4k"))
                routes += helloWorldRoute()
                routes += jsonRoute(clock, random)
            }
        )

fun veryUnsafeCorsFilter() = ServerFilters.Cors(CorsPolicy.UnsafeGlobalPermissive.copy(headers = listOf("*")))

data class JsonMessage(
    val customHeader: String?,
    val now: Instant,
    val randomNumber: Int,
    val bool: Boolean
)

private fun jsonRoute(clock: Clock, random: Random): ContractRoute {
    val header = Header.string().optional("x-custom", "header value")
    val bodyLens = Body.auto<JsonMessage>().toLens()

    return "json" meta {
        description = "This route returns JSON using http4k lenses to provide the JSON Schema example"
        headers += header
        returning(OK, bodyLens to JsonMessage("html", Instant.EPOCH, 1, false))
    } bindContract GET to { req: Request ->
        Response(OK).with(bodyLens of JsonMessage(header(req), Instant.now(clock), random.nextInt(), true))
    }
}

private fun helloWorldRoute() = "hello" meta {
    description = "This is an unsecured route"
    security = NoSecurity
} bindContract GET to { req: Request -> Response(OK).body("hello world!") }

fun main() {
    OpenApi(Clock.systemDefaultZone(), Random(0)).asServer(SunHttp(8080)).start()
}
