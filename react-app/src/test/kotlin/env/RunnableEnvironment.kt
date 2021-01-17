package env

import org.http4k.examples.Http4kReactApp
import org.http4k.server.SunHttp
import org.http4k.server.asServer

/**
 * This development environment pulls in the React assets as well as the backend code.
 */
fun main() {
    Http4kReactApp().asServer(SunHttp(8000)).start()
}
