package isel.gomuku.services.local.gameLogic

import kotlin.math.sqrt

const val BOARD_SIZE = 15
const val MAX_MOVES = BOARD_SIZE * BOARD_SIZE
const val VICTORY_LINE = 5

abstract class Board {
    abstract val moves: MutableMap<Position, Player?>
    abstract val lastPlayer: Player
    val size get() = sqrt(moves.size.toDouble()).toInt()
    abstract fun play(pos: Position, player: Player): Board
    companion object{
        fun startBoard(gridPositions: Int): MutableMap<Position, Player?> {
            val board = mutableMapOf<Position, Player?>()
            repeat(gridPositions) {
                board.put(it.toPosition(), null)
            }
            return board
        }
    }

}

class BoardDraw(
    override val moves: MutableMap<Position, Player?>,
    override val lastPlayer: Player
) : Board() {

    override fun play(pos: Position, player: Player): Board {
        throw IllegalStateException("This game has already finished with a draw.")
    }
}

class BoardWinner(
    override val moves: MutableMap<Position, Player?>,
    override val lastPlayer: Player
) : Board() {
    override fun play(pos: Position, player: Player): Board {
        throw IllegalStateException("The player $lastPlayer won this game.")
    }
}

class BoardRun(override val moves: MutableMap<Position, Player?>, override val lastPlayer: Player) :
    Board() {
    override fun play(pos: Position, player: Player): Board {
        require(lastPlayer != player) { "Player $player cannot play twice!" }
        require(!moves.contains(pos)) {"Position already occupied!"}
        moves.set(pos, player)
        return when {
            checkWinner(player, pos) -> BoardWinner(moves, player)
            moves.size == MAX_MOVES - 1 -> BoardDraw(moves, lastPlayer)
            else -> BoardRun(moves, player)
        }
    }

    private fun checkWinner(p: Player, pos: Position): Boolean {
        val plMoves = moves.toList().filter { it.second == p }.map { it.first }
        val addLastPlay = 1
        return checkWinCondition(
            countPieces(plMoves, pos, Pair(0, 1)) + countPieces(
                plMoves,
                pos,
                Pair(0, -1)
            ) + addLastPlay
        )
                || checkWinCondition(
            countPieces(plMoves, pos, Pair(1, 0)) + countPieces(
                plMoves,
                pos,
                Pair(-1, 0)
            ) + addLastPlay
        )
                || checkWinCondition(
            countPieces(plMoves, pos, Pair(1, 1)) + countPieces(
                plMoves,
                pos,
                Pair(-1, -1)
            ) + addLastPlay
        )
                || checkWinCondition(
            countPieces(plMoves, pos, Pair(-1, 1)) + countPieces(
                plMoves,
                pos,
                Pair(1, -1)
            ) + addLastPlay
        )
    }

    private fun checkWinCondition(int: Int): Boolean =
        int >= VICTORY_LINE


    private fun countPieces(
        moves: List<Position>,
        lastPlay: Position,
        direction: Pair<Int, Int>,
        counter: Int = 0
    ): Int {
        val checkPos: Position = (lastPlay + direction) ?: return counter
        val position = moves.find { move -> move == checkPos } ?: return counter
        return countPieces(moves, position, direction, counter + 1)
    }
}