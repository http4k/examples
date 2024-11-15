package hexagonal

import hexagonal.api.MarketApi
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    MarketApi().asServer(Undertow(8000)).start()
}
