package env

import env.entrylogger.FakeEntryLogger
import env.oauthserver.TestingOAuthServer
import env.userdirectory.FakeUserDirectory
import org.http4k.config.Environment
import org.http4k.core.Credentials
import org.http4k.core.Uri
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import verysecuresystems.EmailAddress
import verysecuresystems.Id
import verysecuresystems.SecurityMcpServer
import verysecuresystems.Settings.ENTRY_LOGGER_URL
import verysecuresystems.Settings.OAUTH_SERVER_URL
import verysecuresystems.Settings.PORT
import verysecuresystems.Settings.USER_DIRECTORY_URL
import verysecuresystems.User
import verysecuresystems.Username
import java.time.Clock

fun main() {
    val securityServerPort = 9000
    val userDirectoryPort = 10000
    val entryLoggerPort = 11000
    val oauthServerPort = 12000

    FakeUserDirectory().apply {
        contains(User(Id.of(0), Username.of("Bob"), EmailAddress.of("bob@bob.com")))
        contains(User(Id.of(1), Username.of("Rita"), EmailAddress.of("rita@bob.com")))
        contains(User(Id.of(2), Username.of("Sue"), EmailAddress.of("sue@bob.com")))
    }.asServer(SunHttp(userDirectoryPort)).start()

    FakeEntryLogger().asServer(SunHttp(entryLoggerPort)).start()

    val clock = Clock.systemUTC()

    TestingOAuthServer(
        Credentials("securityServer", "securityServerSecret"),
        authServerUri = Uri.of("http://localhost:${oauthServerPort}"), clock
    ).asServer(SunHttp(oauthServerPort)).start()

    val env = Environment.defaults(
        PORT of securityServerPort,
        USER_DIRECTORY_URL of Uri.of("http://localhost:$userDirectoryPort"),
        ENTRY_LOGGER_URL of Uri.of("http://localhost:$entryLoggerPort"),
        OAUTH_SERVER_URL of Uri.of("http://localhost:$oauthServerPort")
    )
    SecurityMcpServer(env).start()
}
