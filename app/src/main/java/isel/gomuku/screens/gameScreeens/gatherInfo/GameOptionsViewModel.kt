package isel.gomuku.screens.gameScreeens.gatherInfo

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import isel.gomuku.screens.gameScreeens.GameOptions
import isel.gomuku.screens.gameScreeens.localGame.LocalGameActivity

class GameOptionsViewModel : ViewModel() {
    var game: GameOptions by mutableStateOf(GameOptions())
    private fun buildingGame(changeBoard : (GameOptions) -> Unit){
        val checkBoard = game
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

    fun changeGameType(remoteGame: Boolean) {
        buildingGame {
            game = it.copy(isGameLocal = remoteGame)
        }
    }

    fun startGame(activity: ComponentActivity){
        if (game.isGameLocal){
            LocalGameActivity.navigate(activity)
        }else{
            TODO("Call remote activity with parameters")
        }
    }
}
