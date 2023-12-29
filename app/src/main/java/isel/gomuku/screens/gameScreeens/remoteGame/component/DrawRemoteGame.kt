package isel.gomuku.screens.gameScreeens.remoteGame.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import isel.gomuku.screens.component.LoadingWithText
import isel.gomuku.screens.gameScreeens.components.DrawBoard
import isel.gomuku.screens.gameScreeens.remoteGame.Polling
import isel.gomuku.services.http.game.httpModel.UserInfo
import isel.gomuku.services.local.gameLogic.Player
import isel.gomuku.services.local.gameLogic.Position

@Composable
fun DrawRemoteGame(
    modifier: Modifier,
    gridSize: Int,
    makePlay: (Position) -> Unit,
    moves: MutableMap<Position, Player?>?,
    quit: () -> Unit,
    polling: Polling,
    player: Player?,
    opponent: UserInfo?
){
    val cellSize = (LocalConfiguration.current.screenWidthDp / gridSize) - 1
    Column(modifier = modifier) {

        DrawBoard(
            modifier = Modifier,
            boardSize = gridSize,
            makePlay = makePlay,
            moves = moves,
            cellSize
        )
        
        Row() {
            Box(modifier = Modifier.fillMaxWidth()){
                val color = when (player) {
                    Player.BLACK -> Color.Black
                    Player.WHITE -> Color.White
                    else -> null
                }
                if (color != null) {
                Column(modifier= Modifier.align(Alignment.CenterStart).background(Color.LightGray)) {
                        Text(text = "You're playing as")
                        Box(
                            modifier = Modifier
                                .size(cellSize.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
            }
                Column(modifier= Modifier.align(Alignment.CenterEnd)) {
                    if (opponent != null)
                        Text(text = "You're playing against ${opponent.nickname}")
                }
            }
            
        }

        Box(modifier = Modifier.fillMaxSize()){
            if(polling.isPolling){
                LoadingWithText(text = polling.reason, modifier = Modifier.align(Alignment.TopCenter))
            }
            Button(onClick = quit,modifier= Modifier.align(Alignment.BottomCenter)) {
                Text("Give up")
            }
        }

    }

}