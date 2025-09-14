package merge_signals

import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    badApples().asServer(Jetty(8999)).start()
    println("Server launched on http://localhost:8999")
}
