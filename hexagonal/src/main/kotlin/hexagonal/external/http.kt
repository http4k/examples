package hexagonal.external

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import hexagonal.domain.Notifications
import org.http4k.cloudnative.RemoteRequestFailed
import org.http4k.core.HttpHandler
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom

fun Notifications.Companion.Http(uri: Uri, _http: HttpHandler) = object : Notifications {

    private val http = SetBaseUriFrom(uri).then(_http)

    override fun notify(phoneNumber: String, message: String): Result4k<Int, Exception> {
        val response = http(
            Request(POST, "/notify")
                .query("phone", phoneNumber)
                .query("message", message)
        )
        return when {
            response.status.successful -> Success(response.bodyString().toInt())
            else -> Failure(RemoteRequestFailed(response.status, "failed"))
        }
    }
}
