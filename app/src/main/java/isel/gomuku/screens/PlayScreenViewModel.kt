package isel.gomuku.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.gomuku.gameLogic.Position
import isel.gomuku.screens.component.HttpViewModel
import isel.gomuku.services.GomokuService
import kotlinx.coroutines.launch

open class Game

data class GameOptions(
    val gridSize: Int? = null,
    val variant: GameVariants? = null,
    val openingRule: OpeningRules? = null
) : Game(){
    val readyToStartGame = gridSize != null && variant != null && openingRule != null
}

class a


class PlayScreenViewModel() : HttpViewModel() {

    val service by lazy {
        GomokuService()
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
        request { game = service.startGame() }

    }
    fun play(pos: Position) {
        //request { board = board?.play(pos, board.lastPlayer.turn()) }
    }

    fun quit() {
        request { }
    }


}