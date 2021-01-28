package com.example

import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    Hotwire(false).asServer(Netty(9090)).start()
}
