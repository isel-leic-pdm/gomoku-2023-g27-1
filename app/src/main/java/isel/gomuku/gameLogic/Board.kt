package isel.gomuku.gameLogic

const val BOARD_SIZE = 15
const val MAX_MOVES = BOARD_SIZE * BOARD_SIZE
const val VICTORY_LINE = 5
const val EXACTLY_VICTORY_LINE = true

/*
    companion object {
        fun deserialize(input: String): isel.gomuku.GameLogic.Board {
            val lines = input.split("\n")
            val kind = lines[0]
            val moves = lines.drop(1).filter { it.isNotEmpty() }.map { isel.gomuku.gameLogic.Move.deserialize(it) }
            val lastPlayer = if(moves.isEmpty()) isel.gomuku.gameLogic.Player.WHITE else moves.last().player
            return when (kind) {
                isel.gomuku.GameLogic.BoardRun::class.simpleName -> isel.gomuku.GameLogic.BoardRun(moves, lastPlayer)
                isel.gomuku.GameLogic.BoardDraw::class.simpleName -> isel.gomuku.GameLogic.BoardDraw(moves)
                isel.gomuku.GameLogic.BoardWinner::class.simpleName -> isel.gomuku.GameLogic.BoardWinner(moves, lastPlayer)
                else -> { throw IllegalArgumentException("There is no board type for input $kind")}
            }
        }
    }
    fun serialize() = "${this::class.simpleName}\n${moves.joinToString("\n") { it.serialize() }}"
 */

sealed class Board(val moves: List<Move>) {
    abstract fun play(pos: Position, player: Player): Board

    fun get(pos: Position): Move? {
        return moves.find { it.pos == pos }
    }
}

class BoardDraw(moves: List<Move>) : Board(moves) {
    override fun play(pos: Position, player: Player): Board {
        throw IllegalStateException("This game has already finished with a draw.")
    }
}

class BoardWinner(moves: List<Move>, val winner: Player) : Board(moves) {
    override fun play(pos: Position, player: Player): Board {
        throw IllegalStateException("The player $winner won this game.")
    }
}

class BoardRun(moves: List<Move> = emptyList(), val lastPlayer: Player = Player.WHITE) : Board(moves) {
    override fun play(pos: Position, player: Player): Board {
        require(lastPlayer != player) { "isel.gomuku.gameLogic.Player $player cannot play twice!" }
        require(!moves.any { m -> m.pos == pos }) { "isel.gomuku.gameLogic.Position $pos occupied! Please play on an empty position." }
        val m = Move(player, pos)
        return when {
            checkWinner(m, pos) -> BoardWinner(moves + m, player)
            moves.size == MAX_MOVES - 1 -> BoardDraw(moves + m)
            else -> BoardRun(moves + m, player)
        }
    }

    private fun checkWinner(m: Move, pos: Position): Boolean {
        val plMoves = moves.filter { it.player == m.player }
        return checkWinCondition(countPieces(plMoves, pos, Pair(0, 1)) + countPieces(plMoves, pos, Pair(0, -1)) + 1)
                ||checkWinCondition(countPieces(plMoves, pos, Pair(1, 0)) + countPieces(plMoves, pos, Pair(-1, 0)) + 1)
                ||checkWinCondition(countPieces(plMoves, pos, Pair(1, 1)) + countPieces(plMoves, pos, Pair(-1, -1)) + 1)
                ||checkWinCondition(countPieces(plMoves, pos, Pair(-1, 1)) + countPieces(plMoves, pos, Pair(1, -1)) + 1)
    }

    private fun checkWinCondition(int: Int): Boolean{
        return if (EXACTLY_VICTORY_LINE) int == VICTORY_LINE
        else int >= VICTORY_LINE
    }

    private fun countPieces(moves: List<Move>, lastPos: Position, direction: Pair<Int,Int>, counter: Int = 0): Int{
        val checkPos : Position = (lastPos + direction) ?: return counter
        val move = moves.find { move -> move.pos == checkPos } ?: return counter
        return countPieces(moves, move.pos, direction, counter + 1)
    }
}