package gameoflife.model

import kotlin.random.Random

class GameBoard(private val size: Int, private val colours: List<String>) {

    private val DEAD_COLOUR = "white"

    private val cells = MutableList(size * size) { DEAD_COLOUR }

    fun fillCross(centerIdx: Int, userColor: String) {
        setCell(centerIdx, userColor)

        val (row, col) = indexToCoordinates(centerIdx)
        if (row > 0) setCell(row - 1, col, userColor)
        if (col > 0) setCell(row, col - 1, userColor)
        if (col < size - 1) setCell(row, col + 1, userColor)
        if (row < size - 1) setCell(row + 1, col, userColor)
    }

    fun nextGeneration(): GameBoard {
        val nextBoard = GameBoard(size, colours)

        for (idx in 0 until size * size) {
            val (row, col) = indexToCoordinates(idx)
            val cell = getCell(idx)

            val livingNeighbors = mutableListOf<String>()
            for ((dr, dc) in CELL_NEIGHBOURS) {
                val neighborRow = row + dr
                val neighborCol = col + dc

                if (isValidCoordinate(neighborRow, neighborCol)) {
                    val neighborValue = getCell(neighborRow, neighborCol)
                    if (neighborValue != DEAD_COLOUR) {
                        livingNeighbors.add(neighborValue)
                    }
                }
            }

            val isAlive = cell != DEAD_COLOUR
            val neighborCount = livingNeighbors.size

            val nextCellValue = when {
                isAlive && (neighborCount == 2 || neighborCount == 3) -> cell
                !isAlive && neighborCount == 3 ->
                    when {
                        Random.nextFloat() < 0.1 -> colours.random()
                        else -> livingNeighbors.random()
                    }

                else -> DEAD_COLOUR
            }

            nextBoard.setCell(idx, nextCellValue)
        }

        return nextBoard
    }

    fun initializeWithPatterns() {
        addGlider(size / 4, size / 4)
        addGlider(size / 4, 3 * size / 4)
        addGlider(3 * size / 4, size / 4)
        addOscillator(size / 2, size / 2)
    }

    fun toCells() = cells.mapIndexed { id, cellClass -> Cell(id, cellClass) }

    private fun addGlider(x: Int, y: Int, color: String? = null) {
        val colors = listOf("red", "blue", "green", "fuchsia", "purple")

        setCell(y, x, color ?: colors[0])
        setCell(y, x + 1, color ?: colors[1])
        setCell(y, x + 2, color ?: colors[2])
        setCell(y + 1, x, color ?: colors[3])
        setCell(y + 2, x + 1, color ?: colors[4])
    }

    private fun addOscillator(x: Int, y: Int, color: String = "orange") {
        setCell(y, x - 1, color)
        setCell(y, x, color)
        setCell(y, x + 1, color)
    }

    private fun getCell(idx: Int): String = cells.getOrElse(idx) { DEAD_COLOUR }

    private fun getCell(row: Int, col: Int): String =
        if (isValidCoordinate(row, col)) cells[coordinatesToIndex(row, col)] else DEAD_COLOUR

    private fun setCell(idx: Int, value: String) {
        if (idx in 0 until cells.size) {
            cells[idx] = value
        }
    }

    private fun setCell(row: Int, col: Int, value: String) {
        if (isValidCoordinate(row, col)) cells[coordinatesToIndex(row, col)] = value
    }

    private fun coordinatesToIndex(row: Int, col: Int): Int = col + (row * size)
    private fun indexToCoordinates(idx: Int): Pair<Int, Int> = idx / size to idx % size

    private fun isValidCoordinate(row: Int, col: Int): Boolean =
        row >= 0 && col >= 0 && row < size && col < size

    companion object {
        private val CELL_NEIGHBOURS = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
    }
}
