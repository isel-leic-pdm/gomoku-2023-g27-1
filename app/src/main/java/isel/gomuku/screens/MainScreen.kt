package isel.gomuku.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

import isel.gomuku.helpers.MENU_BUTTON_TEXT_SIZE
import isel.gomuku.logic.MenuState

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