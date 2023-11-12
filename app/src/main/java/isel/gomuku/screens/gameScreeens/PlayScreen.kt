/*
package isel.gomuku.screens.gameScreeens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.gatherInfo.SearchMatch
import isel.gomuku.screens.gameScreeens.gatherInfo.GameOptionsViewModel
import isel.gomuku.ui.theme.GomukuTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class PlayActivity : ComponentActivity() {

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, PlayActivity::class.java)
            source.startActivity(intent)
        }
    }
    private val userInput : GameOptionsViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GomukuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = { finish() })) }){}
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            //Center user input
                            when (val game = viewModel.game) {
                                is GameOptions -> SearchMatch(
                                    game,
                                    viewModel::changeGameType,
                                    viewModel::changeGridSize,
                                    viewModel::changeOpeningRule,
                                    viewModel::changeGameVariant,
                                    viewModel::startGame
                                )

                                is RunningGame -> if (game.board != null) {
                                    PlayScreen(
                                        game.board.size, makePlay = { viewModel.play(it) },
                                        moves = game.board.moves
                                    )
                                }else{
                                    TODO("Waiting opponent screen")
                                }
                            }

                        }
                    }
                }
            }
        }
    }


}


fun startBoard(boardSize: Int): MutableMap<Position, Player?> {

    val board = mutableMapOf<Position, Player?>()
    repeat(boardSize * boardSize) {
        board[it.toPosition(boardSize)] = null
    }
    return board
}

fun Int.toPosition(boardSize: Int): Position {
    return Position(this / boardSize, rem(boardSize))

}
*/
