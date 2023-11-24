package isel.gomuku.screens.gameScreeens.remoteGame

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.http.GameService
import isel.gomuku.http.GameServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.gameScreeens.gatherInfo.GameVariants
import isel.gomuku.screens.gameScreeens.gatherInfo.OpeningRules
import isel.gomuku.services.HttpService

@SuppressLint("MutableCollectionMutableState")
class RemoteGameViewModel (val service : GameServiceHttp) : BaseViewModel() {


    var moves : MutableMap<Position, Player?> = initBoard(0);

    fun startGame(gridSize: Int, variants: GameVariants, openingRules: OpeningRules){
        //request { service.startGame(gridSize, variants.name, openingRules.name) }
        moves = initBoard(gridSize)
    }
    fun play(pos: Position) {
        safeCall {
            service.play(pos.lin, pos.col)
        }
    }

    fun quit() {
        //
    }

    fun initBoard(gridSize: Int): MutableMap<Position, Player?> {

        val board = mutableMapOf<Position, Player?>()
        repeat(gridSize * gridSize) {
            board[it.toPosition(gridSize)] = null
        }
        return board
    }

    private fun Int.toPosition(boardSize: Int): Position {
        return Position(this / boardSize, rem(boardSize))

    }
}