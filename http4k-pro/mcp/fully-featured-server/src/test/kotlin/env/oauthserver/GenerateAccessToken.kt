package env.oauthserver

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import org.http4k.core.Credentials
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.security.oauth.server.ClientId
import org.http4k.security.oauth.server.ClientValidator
import org.http4k.security.oauth.server.DefaultAccessTokenResponseRenderer
import org.http4k.security.oauth.server.IdTokens
import org.http4k.security.oauth.server.accesstoken.ClientSecretAccessTokenRequestAuthentication
import org.http4k.security.oauth.server.accesstoken.GenerateAccessTokenForGrantType
import org.http4k.security.oauth.server.accesstoken.GrantType.ClientCredentials
import org.http4k.security.oauth.server.accesstoken.GrantTypesConfiguration
import org.http4k.security.oauth.server.refreshtoken.RefreshTokens
import java.time.Clock

fun GenerateAccessToken(clock: Clock, credentials: Credentials): HttpHandler {

    val accessTokens = SimpleAccessTokens

    val generateAccessTokenForGrantType = GenerateAccessTokenForGrantType(
        NoOpAuthorizationCodes,
        accessTokens, clock, IdTokens.Unsupported, RefreshTokens.Unsupported,
        GrantTypesConfiguration(
            mapOf(
                ClientCredentials to
                    ClientSecretAccessTokenRequestAuthentication(AlwaysOkClientValidator(credentials))
            )
        )
    )

    return { req: Request ->
        generateAccessTokenForGrantType.generate(req)
            .map(DefaultAccessTokenResponseRenderer::invoke)
            .recover { Response(Status.UNAUTHORIZED) }
    }
}

private class AlwaysOkClientValidator(private val credentials: Credentials) : ClientValidator {
    override fun validateClientId(request: Request, clientId: ClientId) = clientId.value == credentials.user

    override fun validateCredentials(request: Request, clientId: ClientId, clientSecret: String) =
        Credentials(clientId.value, clientSecret) == credentials

    override fun validateRedirection(request: Request, clientId: ClientId, redirectionUri: Uri) =
        clientId.value == credentials.user

    override fun validateScopes(request: Request, clientId: ClientId, scopes: List<String>) =
        clientId.value == credentials.user
}
