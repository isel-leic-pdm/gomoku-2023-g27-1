package isel.gomuku.screens.gameScreeens.remoteGame

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.gameLogic.Board
import isel.gomuku.gameLogic.BoardRun
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.httpServices.GameServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.gameScreeens.gatherInfo.GameVariants
import isel.gomuku.screens.gameScreeens.gatherInfo.OpeningRules

@SuppressLint("MutableCollectionMutableState")
class RemoteGameViewModel : BaseViewModel() {

    var board: Board by mutableStateOf(BoardRun(Board.startBoard(15), Player.WHITE))
    fun startGame(
        gridSize: Int,
        variants: GameVariants,
        openingRules: OpeningRules,
        service: GameServiceHttp,
        authentication: String
    ) {
        safeCall {
            service.startGame(gridSize, variants.name, openingRules.name, authentication)
            board = BoardRun(Board.startBoard(gridSize), Player.WHITE)
        }
    }

    fun play(pos: Position, service: GameServiceHttp, authentication: String) {
        safeCall {
            service.play(pos.lin, pos.col, authentication)
            val newBoard = board
            board = newBoard.play(pos, newBoard.lastPlayer.turn())
        }
    }

    fun quit(service: GameServiceHttp, authentication: String) {
        safeCall {
            service.quitGame(authentication)
        }
    }
}