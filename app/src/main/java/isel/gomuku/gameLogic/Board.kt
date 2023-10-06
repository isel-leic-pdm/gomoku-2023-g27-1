package isel.gomuku.gameLogic

const val BOARD_SIZE = 15
const val MAX_MOVES = BOARD_SIZE * BOARD_SIZE
const val VICTORY_LINE = 5

interface Board {
    fun play(pos: Position, player: Player): Board
}

class BoardDraw(val moves: List<Move>) : Board {

    override fun play(pos: Position, player: Player): Board {
        throw IllegalStateException("This game has already finished with a draw.")
    }
}

class BoardWinner(val moves: List<Move>, val winner: Player) : Board {
    override fun play(pos: Position, player: Player): Board {
        throw IllegalStateException("The player $winner won this game.")
    }
}

class BoardRun(val moves: List<Move> = emptyList(), val lastPlayer: Player = Player.WHITE) : Board {
    override fun play(pos: Position, player: Player): Board {
        require(lastPlayer != player) { "Player $player cannot play twice!" }
        require(!moves.any { m -> m.pos == pos }) { "Position $pos occupied! Please play on an empty position." }
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

    private fun checkWinCondition(int: Int): Boolean =
       int >= VICTORY_LINE


    private fun countPieces(moves: List<Move>, lastPlay: Position, direction: Pair<Int,Int>, counter: Int = 0): Int{
        val checkPos : Position = (lastPlay + direction) ?: return counter
        val move = moves.find { move -> move.pos == checkPos } ?: return counter
        return countPieces(moves, move.pos, direction, counter + 1)
    }
}