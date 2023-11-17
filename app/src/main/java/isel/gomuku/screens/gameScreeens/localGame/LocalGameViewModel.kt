package isel.gomuku.screens.gameScreeens.localGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import isel.gomuku.gameLogic.Board
import isel.gomuku.gameLogic.BoardRun
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.screens.component.BaseViewModel

class LocalGameViewModel : BaseViewModel() {
    val GRID_SIZE = 15

    private var board : Board by mutableStateOf(BoardRun(Board.startBoard(GRID_SIZE),Player.WHITE))

    fun play(pos: Position) {
        safeCall{
            val newBoard = board
            board = newBoard.play(pos, newBoard.lastPlayer.turn())
        }
    }

    fun getMoves(): MutableMap<Position, Player?> = board.moves
}
