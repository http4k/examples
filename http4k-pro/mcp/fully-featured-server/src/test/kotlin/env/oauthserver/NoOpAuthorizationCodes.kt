package env.oauthserver

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.security.oauth.server.AuthRequest
import org.http4k.security.oauth.server.AuthorizationCode
import org.http4k.security.oauth.server.AuthorizationCodeDetails
import org.http4k.security.oauth.server.AuthorizationCodes

object NoOpAuthorizationCodes : AuthorizationCodes {
    override fun detailsFor(code: AuthorizationCode): AuthorizationCodeDetails =
        error("NoOpAuthorizationCodes does not store codes")

    override fun create(request: Request, authRequest: AuthRequest, response: Response) =
        error("NoOpAuthorizationCodes does not store codes")
}
