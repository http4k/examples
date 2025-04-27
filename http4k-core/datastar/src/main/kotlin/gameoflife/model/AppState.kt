package gameoflife.model

import java.lang.Thread.sleep
import java.lang.Thread.startVirtualThread

class AppState(boardSize: Int) {
    private var board = GameBoard(boardSize, COLOURS).apply {
        initializeWithPatterns()
    }

    val cells get() = board.toCells()

    fun update(id: Int, sid: Int) {
        val userColor = COLOURS[sid % COLOURS.size]

        synchronized(this) {
            board.fillCross(id, userColor)
        }
    }

    init {
        startVirtualThread {
            runCatching {
                while (true) {
                    sleep(200)

                    synchronized(this) {
                        board = board.nextGeneration()
                    }
                }
            }
        }
    }

    companion object {
        private val COLOURS = listOf("red", "blue", "green", "orange", "fuchsia", "purple")
    }
}
