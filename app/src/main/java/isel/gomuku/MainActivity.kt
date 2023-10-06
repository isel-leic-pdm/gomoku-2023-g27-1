package isel.gomuku

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.MENU_BUTTON_WIDTH
import isel.gomuku.helpers.MENU_PADDING
import isel.gomuku.helpers.MenuState
import isel.gomuku.model.GoPiece
import isel.gomuku.screens.Biography
import isel.gomuku.screens.MainMenu
import isel.gomuku.ui.theme.GomukuTheme
import kotlin.reflect.KProperty

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomukuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    var menu by remember { mutableStateOf(MenuState.MAIN_MENU) }
    var test = remember {
        mutableStateMapOf<Int,GoPiece>()
    }
    when (menu) {
        MenuState.MAIN_MENU -> MainMenu(
            Modifier
                .padding(MENU_PADDING.dp)
                .width(MENU_BUTTON_WIDTH.dp)
        ) {
            menu = it
        }

        MenuState.AUTHORS -> Biography {
            menu = it
        }

        MenuState.PLAY -> GameBoard({test[it] = GoPiece.Black},test) {
            menu = it
        }
    }
}

@Composable
fun GameBoard(makePlay: (Int) -> Unit,test : SnapshotStateMap<Int, GoPiece>, menuClick: (MenuState) -> Unit) {
    Column {
        Button(onClick = { menuClick(MenuState.MAIN_MENU) }) {
            Text(text = "MainMenu")
        }
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier.padding(5.dp)) {
                repeat(10) { column ->
                    Column() {
                        repeat(10) { row ->
                            val cellNumber: Int = "$column$row".toInt()
                            Box(modifier = Modifier
                                .padding(0.5.dp)
                                .border(1.dp, color = Color.Black)
                                .wrapContentSize(Alignment.Center)
                                .clickable {
                                    makePlay(cellNumber)
                                }) {
                                val color = if (test.get(cellNumber) != null) {
                                    Color.Red
                                } else {
                                    Color.White
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


@Preview(showBackground = true)
@Composable
fun Preview() {
    GomukuTheme {
        /*GameBoard({}) {

        }*/
    }
}