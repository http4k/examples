package fakes

import hexagonal.port.InMemoryNotifications
import org.http4k.chaos.ChaoticHttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.asFormatString
import org.http4k.lens.Path
import org.http4k.lens.Query
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

class FakeNotificationsServer : ChaoticHttpHandler() {

    private val notifications = InMemoryNotifications()

    fun notificationsFor(phoneNumber: String) = notifications.sent.filter { it.first == phoneNumber }

    override val app = routes(
        sendNotification(notifications),
        getNotifications(notifications)
    )
}

private fun getNotifications(notifications: InMemoryNotifications): RoutingHttpHandler {
    val phone = Path.of("phoneNumber")

    return "/{phoneNumber}" bind GET to { req: Request ->
        Response(OK).body(asFormatString(
            notifications.sent.filter { it.first == phone(req) }.map { it.second }
        ))
    }
}

private fun sendNotification(notifications: InMemoryNotifications): RoutingHttpHandler {
    var counter = 0

    val phone = Query.required("phoneNumber")
    val message = Query.required("message")

    return "/notify" bind POST to { req: Request ->
        notifications.notify(phone(req), message(req))
        Response(OK).body(counter++.toString())
    }
}
