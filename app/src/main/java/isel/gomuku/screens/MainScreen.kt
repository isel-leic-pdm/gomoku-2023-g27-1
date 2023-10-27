package isel.gomuku.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

import isel.gomuku.helpers.MENU_BUTTON_TEXT_SIZE
import isel.gomuku.helpers.MenuState
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainMenu(modifier: Modifier, authorsHandler: ()-> Unit, playHandle: () -> Unit) {

    Scaffold(topBar = {TopBar(navigationHandlers = NavigationHandlers(navigateToHandler = authorsHandler))}){}

    Column(verticalArrangement = Arrangement.Center) {

        Button(onClick = playHandle,modifier = modifier) {
            Text(text = "Play", fontSize = MENU_BUTTON_TEXT_SIZE.sp)
        }
        Button(onClick = authorsHandler,modifier = modifier ) {
            Text(text = "Authors ", fontSize = MENU_BUTTON_TEXT_SIZE.sp)
        }
        Button(onClick = { /*TODO*/ }, modifier = modifier) {
            Text(text = "Talk", fontSize = MENU_BUTTON_TEXT_SIZE.sp)
        }
    }
}