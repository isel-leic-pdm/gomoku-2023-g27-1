package isel.gomuku.screens.gameScreeens.gatherInfo

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.gameScreeens.GameOptions
import isel.gomuku.screens.gameScreeens.localGame.LocalGameActivity
import isel.gomuku.screens.gameScreeens.remoteGame.RemoteGameActivity

class GameOptionsViewModel : BaseViewModel() {
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

    fun startGame(activity: ComponentActivity,userToken: String? = null){
        safeCall {
            if (game.isGameLocal) {
                game.startGame()
                LocalGameActivity.navigate(activity)
            }else {
                require(userToken != null){ "Please login first"}
                TODO()// RemoteGameActivity.navigate(this, viewModel.game)
            }
        }
    }
}
