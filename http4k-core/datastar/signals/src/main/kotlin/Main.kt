import datastar.BadApples
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    BadApples().asServer(Jetty(8999)).start()
}
