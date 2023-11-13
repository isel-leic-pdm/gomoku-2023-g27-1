package isel.gomuku.screens.gameScreeens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import isel.gomuku.gameLogic.Board
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.screens.gameScreeens.gatherInfo.GameVariants
import isel.gomuku.screens.gameScreeens.gatherInfo.GridSize
import isel.gomuku.screens.gameScreeens.gatherInfo.OpeningRules
import isel.gomuku.services.LocalService

open class Game

data class GameOptions(
    val gridSize: Int? = null,
    val variant: GameVariants? = null,
    val openingRule: OpeningRules? = null,
    val isGameLocal : Boolean = true
) : Game(){
    private var gameStarted = false
    fun startGame() {
        gameStarted = true
    }

    val asGameStarted = gameStarted
    val canStartGame = (gridSize != null && variant != null && openingRule != null) || isGameLocal
}

class RunningGame (val board: Board?, val player: Player): Game(){
}



class PlayScreenViewModel() : ViewModel() {

    val service by lazy {
        LocalService()
    }

    var game: Game by mutableStateOf(GameOptions())

    private fun buildingGame(changeBoard : (GameOptions) -> Unit){
        val checkBoard = game
        if (checkBoard !is GameOptions) throw TODO()
        changeBoard(checkBoard)
    }
    fun changeGridSize(grid: GridSize) {
        buildingGame {
            game = it.copy(gridSize = grid.size)
        }
    }

    fun changeOpeningRule(rule: OpeningRules) {
        buildingGame {
            game = it.copy(openingRule = rule)
        }
    }

    fun changeGameVariant(variants: GameVariants){
        buildingGame {
            game = it.copy(variant = variants)
        }
    }

    fun startGame(){
       // game = service.startGame()

    }
    fun play(pos: Position) {
        //request { board = board?.play(pos, board.lastPlayer.turn()) }
    }

    fun quit() {
        //
    }

    fun changeGameType(remoteGame: Boolean) {
        buildingGame {
            game = it.copy(isGameLocal = remoteGame)
        }
    }


}