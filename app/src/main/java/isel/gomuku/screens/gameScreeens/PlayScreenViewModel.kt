package isel.gomuku.screens.gameScreeens

import android.os.Parcelable
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
import kotlinx.android.parcel.Parcelize

open class Game

@Parcelize
data class GameOptions(
    val gridSize: Int? = null,
    val variant: GameVariants? = null,
    val openingRule: OpeningRules? = null,
    val isGameLocal : Boolean = true
) : Game(), Parcelable {
    private var gameStarted = false
    fun startGame() {
        gameStarted = true
    }

    val asGameStarted = gameStarted
    val canStartGame = (gridSize != null && variant != null && openingRule != null) || isGameLocal
}

