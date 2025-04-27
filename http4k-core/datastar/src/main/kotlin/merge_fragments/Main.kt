package merge_fragments

import org.http4k.server.Helidon
import org.http4k.server.asServer

fun main() {
    UserManagement().asServer(Helidon(8999)).start()
}
