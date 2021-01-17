package org.http4k.examples

import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    Http4kReactApp().asServer(SunHttp(8000)).start()
}
