package env.oauthserver

import org.http4k.core.Credentials
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.extend
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.security.oauth.metadata.AuthMethod
import org.http4k.security.oauth.metadata.ServerMetadata
import org.http4k.security.oauth.server.AuthorizationServerWellKnown
import java.time.Clock
import java.util.Locale

fun TestingOAuthServer(credentials: Credentials, authServerUri: Uri, clock: Clock): HttpHandler {

    val serverMetadata = serverMetadata(authServerUri)

    return routes(
        AuthorizationServerWellKnown(serverMetadata),
        serverMetadata.token_endpoint.path bind GenerateAccessToken(clock, credentials)
    )
}

private fun serverMetadata(uri: Uri) = ServerMetadata(
    issuer = uri.authority,
    authorization_endpoint = uri.extend(Uri.of("/oauth/authorize")),
    token_endpoint = uri.extend(Uri.of("/oauth/token")),
    token_endpoint_auth_methods_supported = listOf(AuthMethod.client_secret_basic),
    token_endpoint_auth_signing_alg_values_supported = listOf("RS256"),
    response_types_supported = listOf(),
    ui_locales_supported = listOf(Locale.forLanguageTag("en-GB"))
)

