package environment.oauthserver

import org.http4k.core.Credentials
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.security.oauth.server.ClientId
import org.http4k.security.oauth.server.ClientValidator

data class OAuthClientData(val credentials: Credentials, val redirectionUri: Uri)

object SimpleClientValidator {
    operator fun invoke(vararg clientData: OAuthClientData) = object : ClientValidator {

        override fun validateClientId(request: Request, clientId: ClientId) = clientData.any { clientId.value == it.credentials.user }

        override fun validateCredentials(request: Request, clientId: ClientId, clientSecret: String) = clientData.any { Credentials(clientId.value, clientSecret) == it.credentials }

        override fun validateRedirection(request: Request, clientId: ClientId, redirectionUri: Uri) = clientData.any { validateClientId(request, clientId) && redirectionUri == it.redirectionUri }

        override fun validateScopes(request: Request, clientId: ClientId, scopes: List<String>) = true
    }
}
