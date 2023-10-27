package isel.gomuku.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import isel.gomuku.R
import isel.gomuku.gameLogic.BoardDraw
import isel.gomuku.gameLogic.BoardWinner
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.services.Container
import isel.gomuku.services.GomokuService
import isel.gomuku.ui.theme.GomukuTheme


class PlayActivity : ComponentActivity() {

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, PlayActivity::class.java)
            source.startActivity(intent)
        }
    }

    private val service: GomokuService = GomokuService()
    //by lazy{ (this.application as Container).gomokuService }
    private val board: PlayScreenViewModel by viewModels(){PlayScreenViewModel.Factory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GomukuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    PlayScreen(
                        board.boardSize, makePlay = { board.play(it, service) },
                        moves = board.board.moves
                    ) { if (board.board !is BoardWinner && board.board !is BoardDraw)
                            board.quit(service)
                        this.finish() }

                    ErrorMessage(board.error) { board.error = null }
                    LoadingScreen(board.waiting)
                }
            }
        }
    }
}

@Composable
fun PlayScreen(
    boardSize: Int,
    makePlay: (Position) -> Unit,
    moves: MutableMap<Position, Player?>,
    quit: () -> Unit
) {
    Column {
        Button(onClick = {quit}) {
            Text("Quit")
        }
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier.padding(5.dp)) {
                repeat(boardSize) { column ->
                    Column() {
                        repeat(boardSize) { row ->
                            val pos = Position.invoke(row, column)
                            Box(modifier = Modifier
                                .padding(0.5.dp)
                                .border(1.dp, color = Color.Black)
                                .wrapContentSize(Alignment.Center)
                                .clickable() { makePlay(pos) }
                                .background(Color.Green)) {
                                val color = when (moves[pos]) {
                                    Player.BLACK -> Color.Black
                                    Player.WHITE -> Color.Red
                                    else -> Color.White
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