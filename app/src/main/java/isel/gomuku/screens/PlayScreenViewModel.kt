package isel.gomuku.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import isel.gomuku.gameLogic.Position
import isel.gomuku.services.GomokuService

open class Game

data class GameOptions(
    val gridSize: Int? = null,
    val variant: GameVariants? = null,
    val openingRule: OpeningRules? = null
) : Game(){
    val readyToStartGame = gridSize != null && variant != null && openingRule != null
}

class a


class PlayScreenViewModel() : ViewModel() {

    val service by lazy {
        GomokuService()
    }

    var game: Game by mutableStateOf(GameOptions())
    var waiting by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    private fun request(httpRequest: () -> Unit) {
        waiting = true
        try {
            //adicionar pedido http
            httpRequest()
        } catch (e: Exception) {
            error = e.message
        }
        waiting = false
    }
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
        game = service.startGame()
    }
    fun play(pos: Position) {
        //request { board = board?.play(pos, board.lastPlayer.turn()) }
    }

    fun quit() {
        request { }
    }


}