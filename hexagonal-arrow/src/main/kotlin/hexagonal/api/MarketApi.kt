package hexagonal.api

import hexagonal.adapter.database.Database
import hexagonal.adapter.http.Http
import hexagonal.domain.MarketHub
import hexagonal.port.Notifications
import hexagonal.port.PhoneBook
import hexagonal.port.Users
import org.http4k.client.OkHttp
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.routing.routes

fun MarketApi(
    phoneBook: PhoneBook,
    users: Users,
    notifications: Notifications
) = routes(
    MarkDispatched(MarketHub(phoneBook, users, notifications))
)

fun MarketApi(
    notificationsUri: Uri = Uri.of("https://notifications"),
    http: HttpHandler = OkHttp()
) = MarketApi(
    PhoneBook.Database(),
    Users.Database(),
    Notifications.Http(notificationsUri, http)
)
