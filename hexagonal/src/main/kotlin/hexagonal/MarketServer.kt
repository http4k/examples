package hexagonal

import hexagonal.api.MarketApi
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    MarketApi().asServer(SunHttp(8000)).start()
}
