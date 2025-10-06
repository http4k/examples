package hexagonal.api

import arrow.core.getOrElse
import hexagonal.domain.DispatchResult
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
    val (senderId, recipientId, trackingNumber) = body(req)
    transactions.markDispatched(senderId, recipientId, trackingNumber)
        .map { Response(OK) }
        .getOrElse { error: DispatchResult ->
            when (error) {
                is UserNotFound -> Response(NOT_FOUND)
                is OtherFailure -> Response(SERVICE_UNAVAILABLE).body(error.message)
            }
        }
}

data class DispatchMessage(val senderId: Int, val recipientId: Int, val trackingNumber: String)

private val body = Body.auto<DispatchMessage>().toLens()
