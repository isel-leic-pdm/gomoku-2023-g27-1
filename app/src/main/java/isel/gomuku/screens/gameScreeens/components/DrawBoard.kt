package isel.gomuku.screens.gameScreeens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position

@Composable
fun DrawBoard(
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