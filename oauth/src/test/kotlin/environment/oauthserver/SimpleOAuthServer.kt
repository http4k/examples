package environment.oauthserver

import environment.oauthserver.SimpleOAuthServer.Form.formLens
import environment.oauthserver.SimpleOAuthServer.Form.password
import environment.oauthserver.SimpleOAuthServer.Form.username
import org.http4k.core.Body
import org.http4k.core.Credentials
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.SEE_OTHER
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.Header
import org.http4k.lens.Validator.Strict
import org.http4k.lens.webForm
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.security.oauth.server.OAuthServer
import java.time.Clock

object SimpleOAuthServer {
    operator fun invoke(credentials: Credentials, vararg oAuthClientData: OAuthClientData): HttpHandler {
        val clock = Clock.systemUTC()
        val server = OAuthServer(
            "/oauth2/token",
            InMemoryAuthRequestTracking(),
            SimpleClientValidator(*oAuthClientData),
            InMemoryAuthorizationCodes(clock),
            SimpleAccessTokens(),
            clock
        )

        val userAuth = UserAuthentication(credentials)

        return routes(
            server.tokenRoute,
            "/" bind routes(
                GET to server.authenticationStart.then { Response(OK).body(LOGIN_PAGE) },
                POST to { request ->
                    val form = formLens(request)
                    if (userAuth.authenticate(Credentials(username(form), password(form)))) {
                        server.authenticationComplete(request)
                    } else Response(SEE_OTHER).with(Header.LOCATION of request.uri)
                }
            )
        )
    }

    private object Form {
        val username = FormField.required("username")
        val password = FormField.required("password")
        val formLens = Body.webForm(Strict, username, password).toLens()
    }
}

private const val LOGIN_PAGE = """
    <html>
    <b>Please log into the OAuth server (http4k/http4k)</b>
    <form id="loginForm" method="POST">
        <input id="username" type="text" value="http4k" name="username"><br>
        <input id="password" type="password" value="http4k" name="password"><br>
        <button type="submit">Login</button>
    </form>
    </html>
"""

