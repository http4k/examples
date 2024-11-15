package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.Credentials
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.security.OAuthProvider
import org.http4k.security.OAuthProviderConfig
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.time.Clock

fun HellOAuth(
    oauthServerUri: Uri,
    credentials: Credentials,
    oauthServerHttp: HttpHandler = JavaHttpClient(),
    clock: Clock = Clock.systemUTC()
): HttpHandler {
    val oAuthProvider = OAuthProvider(
        OAuthProviderConfig(oauthServerUri, "/", "/oauth2/token", credentials),
        oauthServerHttp,
        Uri.of("http://localhost:8080/oauth/callback"),
        emptyList(),
        InMemoryOAuthPersistence(clock)
    )

    return routes(
        "/oauth/callback" bind GET to oAuthProvider.callback,
        "/" bind GET to oAuthProvider.authFilter.then { Response(OK).body("hello world!") })
}

fun main() {
    HellOAuth(
        Uri.of("http://localhost:10000"),
        Credentials("http4kServer", "http4kServerSecret")
    ).asServer(SunHttp(8080)).start()
}
