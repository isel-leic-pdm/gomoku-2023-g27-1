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
import isel.gomuku.helpers.AUTHORSTEXTSIZE
import isel.gomuku.helpers.MENUBUTTONTEXTSIZE
import isel.gomuku.helpers.MENUBUTTONWIDTH
import isel.gomuku.helpers.MENUPADDING
import isel.gomuku.logic.MenuState
import isel.gomuku.screens.Biography
import isel.gomuku.screens.MainMenu
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
    var menu by remember { mutableStateOf(MenuState.MAINMENU) }
    when (menu) {
        MenuState.MAINMENU -> MainMenu(Modifier.padding(MENUPADDING.dp).width(MENUBUTTONWIDTH.dp)) {
            menu = it
        }
        MenuState.AUTHORS -> Biography {
            menu = it
        }
        MenuState.PLAY -> TODO()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GomukuTheme {
        App()
    }
}