package com.example

import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    Hotwire(false).asServer(SunHttp(9090)).start()
}
