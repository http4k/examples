import datastar.DatastarApp
import org.http4k.server.Helidon
import org.http4k.server.asServer

fun main() {
    DatastarApp().asServer(Helidon(8999)).start()
}
