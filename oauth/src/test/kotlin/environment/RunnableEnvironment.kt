package environment

import com.example.HellOAuth
import environment.oauthserver.OAuthClientData
import environment.oauthserver.SimpleOAuthServer
import org.http4k.core.Credentials
import org.http4k.core.Uri
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val appPort = 8080

    val oauthCreds = Credentials("http4kServer", "http4kServerSecret")

    SimpleOAuthServer(Credentials("http4k", "http4k"),
        OAuthClientData(oauthCreds, Uri.of("http://localhost:$appPort/oauth/callback"))
    ).asServer(SunHttp(10000)).start()

    HellOAuth(Uri.of("http://localhost:10000"), oauthCreds).asServer(SunHttp(appPort)).start()
}
