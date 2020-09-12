package environment.oauthserver

import org.http4k.core.Credentials

class UserAuthentication(private vararg val validCredentials: Credentials) {
    fun authenticate(credentials: Credentials) = credentials in validCredentials
}
