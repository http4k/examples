package hexagonal.api

import hexagonal.domain.DispatchResult.Ok
import hexagonal.domain.DispatchResult.OtherFailure
import hexagonal.domain.DispatchResult.UserNotFound
import hexagonal.domain.MarketHub
import org.http4k.core.Body
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.SERVICE_UNAVAILABLE
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

fun MarkDispatched(transactions: MarketHub) = "/dispatch" bind POST to { req: Request ->
    val command = body(req)
    when (val result = transactions.markDispatched(command.senderId, command.recipientId, command.trackingNumber)) {
        is Ok -> Response(OK)
        is UserNotFound -> Response(NOT_FOUND)
        is OtherFailure -> Response(SERVICE_UNAVAILABLE).body(result.message)
    }
    Response(OK)
}

data class DispatchMessage(val senderId: Int, val recipientId: Int, val trackingNumber: String)

private val body = Body.auto<DispatchMessage>().toLens()
