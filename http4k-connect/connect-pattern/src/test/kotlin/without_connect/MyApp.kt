import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters.BearerAuth
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

fun MyApp(): HttpHandler =
    BearerAuth("my-very-secure-and-secret-bearer-token")
        .then(
            routes(
                echo(),
                health()
            )
        )

fun echo() = "/echo" bind POST to { req: Request -> Response(OK).body(req.bodyString()) }

fun health() = "/health" bind GET to { req: Request -> Response(OK).body("alive!") }

fun main() {
    MyApp().asServer(Netty(8080)).start()
}
