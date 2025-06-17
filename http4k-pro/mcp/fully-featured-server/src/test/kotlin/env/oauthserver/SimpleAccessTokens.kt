package env.oauthserver

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import org.http4k.security.AccessToken
import org.http4k.security.oauth.server.AccessTokens
import org.http4k.security.oauth.server.AuthorizationCodeAlreadyUsed
import org.http4k.security.oauth.server.ClientId
import org.http4k.security.oauth.server.TokenRequest
import org.http4k.security.oauth.server.UnsupportedGrantType
import org.http4k.security.oauth.server.accesstoken.AuthorizationCodeAccessTokenRequest

object SimpleAccessTokens : AccessTokens {
    override fun create(clientId: ClientId, tokenRequest: TokenRequest) =
        Failure(UnsupportedGrantType("client_credentials"))

    override fun create(
        clientId: ClientId,
        tokenRequest: AuthorizationCodeAccessTokenRequest
    ): Result<AccessToken, AuthorizationCodeAlreadyUsed> =
        Success(AccessToken(ACCESS_TOKEN_PREFIX + tokenRequest.authorizationCode.value.reversed()))
}

const val ACCESS_TOKEN_PREFIX = "ACCESS_TOKEN_"
