package isel.gomuku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.gomuku.gui.AUTHORS_TEXT_SIZE
import isel.gomuku.gui.MENU_BUTTON_TEXT_SIZE
import isel.gomuku.gui.MENU_BUTTON_WIDTH
import isel.gomuku.gui.MENU_PADDING
import isel.gomuku.logic.MenuState
import isel.gomuku.ui.theme.GomukuTheme

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
    when (menu) {
        MenuState.MAIN_MENU -> MainMenu(Modifier.padding(MENU_PADDING.dp).width(MENU_BUTTON_WIDTH.dp)) {
            menu = it
        }
        MenuState.AUTHORS -> Biography {
            menu = it
        }
        MenuState.PLAY -> TODO()
    }
}

@Composable
fun Biography(onClick: (MenuState) -> Unit) {
    Column {
        Button(onClick = { onClick(MenuState.MAIN_MENU) }) {
            Text(text = "Main Menu")
        }
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
            Column(modifier = Modifier.fillMaxHeight(), Arrangement.SpaceEvenly) {
                Text(text = "48323 Simão Cabral", fontSize = AUTHORS_TEXT_SIZE.sp)
                Text(text = "49454 Eduardo Tavares", fontSize = AUTHORS_TEXT_SIZE.sp)
                Text(text = "48268 Marçorio Fortes", fontSize = AUTHORS_TEXT_SIZE.sp)
            }
        }
    }
}

@Composable
fun MainMenu(modifier: Modifier, onClick: (MenuState) -> Unit) {
    Column(verticalArrangement = Arrangement.Center) {

        Button(onClick = { /*TODO*/ },modifier = modifier) {
            Text(text = "Play", fontSize = MENU_BUTTON_TEXT_SIZE.sp)
        }
        Button(onClick = { onClick(MenuState.AUTHORS) },modifier = modifier ) {
            Text(text = "Authors", fontSize = MENU_BUTTON_TEXT_SIZE.sp)
        }
        Button(onClick = { /*TODO*/ }, modifier = modifier) {
            Text(text = "Talk", fontSize = MENU_BUTTON_TEXT_SIZE.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GomukuTheme {
        App()
    }
}