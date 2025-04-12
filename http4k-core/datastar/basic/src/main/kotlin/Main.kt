import datastar.app
import org.http4k.server.Helidon
import org.http4k.server.asServer

fun main() {
    app.asServer(Helidon(8000)).start()
}
