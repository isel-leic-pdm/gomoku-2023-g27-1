package isel.gomuku.screens.gameScreeens

import android.os.Parcelable
import isel.gomuku.screens.gameScreeens.gatherInfo.GameVariants
import isel.gomuku.screens.gameScreeens.gatherInfo.OpeningRules
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel


open class Game

@Parcelize
data class GameOptions(
    val gridSize: Int? = null,
    val variant: GameVariants? = null,
    val openingRule: OpeningRules? = null,
    val isGameLocal : Boolean = true
) : Game(), Parcelable {
    @IgnoredOnParcel
    private var gameStarted = false
    fun startGame() {
        gameStarted = true
    }

    @IgnoredOnParcel
    val asGameStarted = gameStarted
    @IgnoredOnParcel
    val canStartGame = (gridSize != null && variant != null && openingRule != null) || isGameLocal
}

