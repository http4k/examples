package gameoflife

import gameoflife.app.GameOfLife
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    GameOfLife(boardSize = 50).asServer(Jetty(8080)).start()
    println("Server launched on http://localhost:8080")
}
