package isel.gomuku.screens.gameScreeens.components

import android.annotation.SuppressLint
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import isel.gomuku.services.local.gameLogic.Player
import isel.gomuku.services.local.gameLogic.Position
import isel.gomuku.ui.theme.GomukuTheme

private const val ROW_PADDING = 5
private const val CELL_PADDING = 0.5
private const val BORDER_WIDTH = 1
private const val CIRCLE_ALIGN = 4


const val bottomRightCorner = "extremity"
const val ignore = "ignore"


@Composable
fun DrawBoard(
    modifier: Modifier,
    boardSize: Int,
    makePlay: (Position) -> Unit,
    moves: MutableMap<Position, Player?>?,
    cellSize : Int
) {
    val backGroundColor = Color(red = 246, green = 206, blue = 5)
    if (moves != null) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(backGroundColor)
                .border(BORDER_WIDTH.dp, Color.Black)
        ) {
            Row(modifier = Modifier.padding(ROW_PADDING.dp)) {

                repeat(boardSize) { row ->
                    Column {
                        repeat(boardSize) { column ->
                            val pos = Position.invoke(row, column)
                            Box(modifier = Modifier
                                .size(cellSize.dp)
                                .padding(CELL_PADDING.dp)
                                .border(BORDER_WIDTH.dp, color = Color.Gray)
                                .wrapContentSize(Alignment.Center)
                                .clickable { makePlay(pos) }
                                .testTag(positionTag(boardSize, pos))
                            ) {
                                val color = when (moves[pos]) {
                                    Player.BLACK -> Color.Black
                                    Player.WHITE -> Color.White
                                    else -> backGroundColor
                                }
                                Box(
                                    modifier = Modifier
                                        .size((cellSize - CIRCLE_ALIGN).dp)
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
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun Preview() {
    GomukuTheme {
        val grid = 15
        val size = (LocalConfiguration.current.screenWidthDp / grid) - 1
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val backGroundColor = Color(red = 246, green = 206, blue = 5)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .background(backGroundColor)
                        .border(BORDER_WIDTH.dp, Color.Black)
                ) {
                    Row(modifier = Modifier.padding(ROW_PADDING.dp)) {
                        repeat(grid) { column ->
                            Column() {
                                repeat(grid) { row ->
                                    Box(modifier = Modifier
                                        .size(size.dp)
                                        .padding(0.5.dp)
                                        .border(BORDER_WIDTH.dp, color = Color.Gray)
                                        .wrapContentSize(Alignment.Center)
                                        .clickable { }
                                    ) {
                                        val color = Color.Black
                                        Box(
                                            modifier = Modifier
                                                .size((size - 4).dp)
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
    }
}


//For testing
//if result is modified change also LocalGameTest
private fun positionTag(gridSize: Int, pos: Position) =
    if (pos.lin == gridSize - 1 && pos.col == gridSize - 1)
        bottomRightCorner
    else ignore
