package isel.gomuku.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.AUTHORS_TEXT_SIZE
import isel.gomuku.helpers.MenuState

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
                Text(text = "XXXXX Marçorio Fortes", fontSize = AUTHORS_TEXT_SIZE.sp)
            }
        }
    }
}