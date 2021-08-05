package hexagonal.api

import hexagonal.database.Database
import hexagonal.domain.MarketHub
import hexagonal.domain.Notifications
import hexagonal.domain.PhoneBook
import hexagonal.domain.Users
import hexagonal.external.Http
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
