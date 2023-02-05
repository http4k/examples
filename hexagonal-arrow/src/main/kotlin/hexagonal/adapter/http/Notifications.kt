package hexagonal.adapter.http

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import hexagonal.port.Notifications
import org.http4k.cloudnative.RemoteRequestFailed
import org.http4k.core.HttpHandler
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom

fun Notifications.Companion.Http(uri: Uri, _http: HttpHandler) = object : Notifications {

    private val http = SetBaseUriFrom(uri).then(_http)

    override fun notify(phoneNumber: String, message: String): Either<Exception, Int> {
        val response = http(
            Request(POST, "/notify")
                .query("phoneNumber", phoneNumber)
                .query("message", message)
        )
        return when {
            response.status.successful -> Right(response.bodyString().toInt())
            else -> Left(RemoteRequestFailed(response.status, "failed"))
        }
    }
}
