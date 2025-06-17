package env

import env.entrylogger.FakeEntryLogger
import env.oauthserver.TestingOAuthServer
import env.userdirectory.FakeUserDirectory
import org.http4k.ai.mcp.client.DiscoveredMcpOAuth
import org.http4k.ai.mcp.client.http.HttpStreamingMcpClient
import org.http4k.ai.mcp.model.McpEntity
import org.http4k.ai.mcp.protocol.Version
import org.http4k.client.JavaHttpClient
import org.http4k.config.Environment
import org.http4k.core.BodyMode.Stream
import org.http4k.core.Credentials
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.filter.debug
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

    val credentials = Credentials("secureClient", "secret")

    TestingOAuthServer(
        credentials,
        authServerUri = Uri.of("http://localhost:${oauthServerPort}"), clock
    ).asServer(SunHttp(oauthServerPort)).start()

    SecurityMcpServer(
        Environment.defaults(
            PORT of securityServerPort,
            USER_DIRECTORY_URL of Uri.of("http://localhost:$userDirectoryPort"),
            ENTRY_LOGGER_URL of Uri.of("http://localhost:$entryLoggerPort"),
            OAUTH_SERVER_URL of Uri.of("http://localhost:$oauthServerPort")
        )
    ).start()

    val http = ClientFilters.DiscoveredMcpOAuth(credentials)
        .then(JavaHttpClient(responseBodyMode = Stream))
//            .debug()

    HttpStreamingMcpClient(
        McpEntity.of("foo"),
        Version.of("1.0.0"),
        Uri.of("http://localhost:$securityServerPort/mcp"),
        http
    ).apply {
        println(">>> Server handshake\n" + start())
        println(">>> Tool list\n" + tools().list())
        println(">>> Prompt list\n" + prompts().list())
        println(">>> Resource list\n" + resources().list())
    }
}
