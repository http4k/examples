package verysecuresystems

import org.http4k.ai.mcp.protocol.ServerMetaData
import org.http4k.ai.mcp.server.security.OAuthMcpSecurity
import org.http4k.core.HttpHandler
import org.http4k.core.PolyHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.events.EventFilters.AddTimestamp
import org.http4k.events.EventFilters.AddZipkinTraces
import org.http4k.events.Events
import org.http4k.events.then
import org.http4k.filter.ClientFilters.RequestTracing
import org.http4k.filter.ClientFilters.SetHostFrom
import org.http4k.filter.HandleRemoteRequestFailed
import org.http4k.filter.ServerFilters
import org.http4k.routing.mcpHttpStreaming
import verysecuresystems.diagnostic.Auditor
import verysecuresystems.external.EntryLogger
import verysecuresystems.external.UserDirectory
import verysecuresystems.tools.UserTools
import java.time.Clock

fun SecurityMcp(
    clock: Clock,
    events: Events,
    http: HttpHandler,
    oauthServerUri: Uri,
    userDirectoryUri: Uri,
    entryLoggerUri: Uri
): PolyHandler {
    val timedEvents = AddZipkinTraces()
        .then(AddTimestamp(clock))
        .then(events)

    val timedHttp = RequestTracing().then(Auditor.Outgoing(timedEvents)).then(http)

    val inhabitants = Inhabitants()

    val userDirectory = UserDirectory(SetHostFrom(userDirectoryUri).then(timedHttp))

    val entryLogger = EntryLogger(SetHostFrom(entryLoggerUri).then(timedHttp), clock)

    // Create the application "stack", including inbound auditing
    return ServerFilters.CatchAll()
        .then(ServerFilters.RequestTracing())
        .then(Auditor.Incoming(timedEvents))
        .then(ServerFilters.HandleRemoteRequestFailed())
        .then(
            mcpHttpStreaming(
                ServerMetaData("SecurityServerMcp", "1.0.0"),
                OAuthMcpSecurity(oauthServerUri) {
                    it.startsWith("ACCESS_TOKEN")
                },
                UserTools(userDirectory, entryLogger, inhabitants)
            )
        )
}
