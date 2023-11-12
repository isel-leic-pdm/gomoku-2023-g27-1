package isel.gomuku.screens.gameScreeens.localGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import isel.gomuku.gameLogic.Board
import isel.gomuku.gameLogic.BoardRun
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position

class LocalGameViewModel : ViewModel() {
    val GRID_SIZE = 15

    val moves : MutableMap<Position,Player?>
    init {
        moves = initBoard()
    }

    var board : Board by mutableStateOf(BoardRun(moves,Player.WHITE))

    fun play(pos: Position) {
         board = board.play(pos, board.lastPlayer.turn())
    }

    private fun initBoard(): MutableMap<Position, Player?> {

        val board = mutableMapOf<Position, Player?>()
        repeat(GRID_SIZE * GRID_SIZE) {
            board[it.toPosition(GRID_SIZE)] = null
        }
        return board
    }

    fun Int.toPosition(boardSize: Int): Position {
        return Position(this / boardSize, rem(boardSize))

    }
}
