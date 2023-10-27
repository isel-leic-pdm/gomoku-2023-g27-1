package isel.gomuku.gameLogic

const val LAST_COORD = BOARD_SIZE - 1

data class Position private constructor(val lin: Int, val col: Int) {

    companion object {
        val values = (0 until MAX_MOVES).map { index -> Position(index / BOARD_SIZE, index % BOARD_SIZE) }
        operator fun invoke(l: Int, c: Int) : Position {
            require(l in 0 until BOARD_SIZE) { "Illegal coordinates must be between 0 and $LAST_COORD" }
            require(c in 0 until BOARD_SIZE) { "Illegal coordinates must be between 0 and $LAST_COORD" }
            val index = l * BOARD_SIZE + c
            return values[index]
        }
    }

    override fun toString(): String {
        return "($lin,$col)"
    }
    operator fun plus (dir: Pair<Int, Int>): Position?{
        val line = this.lin + dir.first
        if (line < 0 || line >= BOARD_SIZE) return null
        val column = this.col + dir.second
        if (column < 0 || column >=  BOARD_SIZE) return null
        return  Position(line, column)
    }

}
fun Int.toPosition(): Position{
    return Position.invoke(this/10,rem(10))

}