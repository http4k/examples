package verysecuresystems.external

import org.http4k.ai.mcp.util.McpJson.auto
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ClientFilters
import org.http4k.filter.HandleRemoteRequestFailed
import verysecuresystems.UserEntry
import verysecuresystems.Username
import java.time.Clock

/**
 * Business abstraction for the external EntryLogger service. Uses JSON-automarshalling via
 * Jackson to convert objects to Kotlin data-class instances
 */
class EntryLogger(http: HttpHandler, private val clock: Clock) {

    // this filter will handle and rethrow non-successful HTTP responses
    private val http = ClientFilters.HandleRemoteRequestFailed().then(http)

    private val userEntry = Body.auto<UserEntry>().toLens()

    fun enter(username: Username): UserEntry =
        userEntry(
            http(
                Request(POST, "/entry")
                    .with(userEntry of UserEntry(username.value, true, clock.instant().toEpochMilli()))
            )
        )

    fun exit(username: Username): UserEntry =
        userEntry(
            http(
                Request(POST, "/exit")
                    .with(userEntry of UserEntry(username.value, false, clock.instant().toEpochMilli()))
            )
        )
}
