package verysecuresystems.external

import org.http4k.ai.mcp.util.McpJson.auto
import org.http4k.cloudnative.RemoteRequestFailed
import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.APPLICATION_FORM_URLENCODED
import org.http4k.core.HttpHandler
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.ACCEPTED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ClientFilters
import org.http4k.filter.HandleRemoteRequestFailed
import org.http4k.lens.Header.CONTENT_TYPE
import verysecuresystems.EmailAddress
import verysecuresystems.Id
import verysecuresystems.User
import verysecuresystems.Username

/**
 * Business abstraction for the external UserDirectory service. Uses JSON-automarshalling via
 * Jackson to convert objects to Kotlin data-class instances
 */
class UserDirectory(http: HttpHandler) {

    // this filter will handle and rethrow non-successful HTTP responses
    private val http = ClientFilters.HandleRemoteRequestFailed({ status.successful || status == NOT_FOUND }).then(http)

    private val users = Body.auto<List<User>>().toLens()
    private val user = Body.auto<User>().toLens()

    fun create(name: Username, inEmail: EmailAddress): User =
        user(
            http(
                Request(POST, "/user")
                    .with(CONTENT_TYPE of APPLICATION_FORM_URLENCODED)
                    .form("email", inEmail.value)
                    .form("username", name.value)
            )
        )

    fun delete(idToDelete: Id) =
        with(http(Request(DELETE, "/user/${idToDelete.value}"))) {
            if (status != ACCEPTED) throw RemoteRequestFailed(status, "user directory")
        }

    fun list(): List<User> = users(http(Request(GET, "/user")))

    fun lookup(username: Username): User? =
        with(http(Request(GET, "/user/${username.value}"))) {
            when (status) {
                NOT_FOUND -> null
                OK -> user(this)
                else -> throw RemoteRequestFailed(status, "user directory")
            }
        }
}
