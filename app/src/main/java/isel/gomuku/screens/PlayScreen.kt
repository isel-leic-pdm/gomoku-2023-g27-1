package isel.gomuku.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import isel.gomuku.R
import isel.gomuku.gameLogic.OpenGame
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.ui.theme.GomukuTheme


class PlayActivity : ComponentActivity() {

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, PlayActivity::class.java)
            source.startActivity(intent)
        }
    }

    //private val service: GomokuService = GomokuService()
    //by lazy{ (this.application as Container).gomokuService }
    private val gameScreen: PlayScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GomukuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column {

                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            //Center user input
                            val game = gameScreen.game
                            when (game) {
                                is GameOptions -> SearchMatch(
                                    game,
                                    gameScreen::changeGridSize,
                                    gameScreen::changeOpeningRule,
                                    gameScreen::changeGameVariant
                                )

                                is OpenGame -> if (game.board != null) {
                                    PlayScreen(
                                        game.board.size, makePlay = { gameScreen.play(it) },
                                        moves = game.board.moves
                                    )
                                }else{
                                    TODO()
                                }
                            }

                        }
                    }
                }


                ErrorMessage(gameScreen.error) { gameScreen.error = null }
                LoadingScreen(gameScreen.waiting)
            }
        }
    }


}


@Composable
fun PlayScreen(
    boardSize: Int,
    makePlay: (Position) -> Unit,
    moves: MutableMap<Position, Player?>
) {

    val backGroundColor = Color(red = 246, green = 206, blue = 5)
    if (moves != null) {
        Box(modifier = Modifier.background(backGroundColor)) {
            Row(modifier = Modifier.padding(5.dp)) {
                repeat(boardSize) { column ->
                    Column() {
                        repeat(boardSize) { row ->
                            val pos = Position.invoke(row, column)
                            Box(modifier = Modifier
                                .padding(0.5.dp)
                                .border(1.dp, color = Color.Black)
                                .wrapContentSize(Alignment.Center)
                                .clickable { makePlay(pos) }
                            ) {
                                val color = when (moves[pos]) {
                                    Player.BLACK -> Color.Black
                                    Player.WHITE -> Color.Red
                                    else -> backGroundColor
                                }
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun LoadingScreen(isLoading: Boolean) {
    if (!isLoading) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.8f))
    ) {
        Text(
            text = stringResource(id = R.string.Loading),
            modifier = Modifier.align(Alignment.Center)
        )
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

@Composable
private fun ErrorMessage(error: String?, onClick: () -> Unit) {
    if (error == null) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = 0.5f))
    ) {
        Text(text = error, modifier = Modifier.align(Alignment.Center))
        Button(
            onClick = onClick, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ) {
            Text(text = stringResource(id = R.string.Dismiss))
        }
    }
}