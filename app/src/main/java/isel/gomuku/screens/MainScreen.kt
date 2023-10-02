package isel.gomuku.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.MENUBUTTONTEXTSIZE
import isel.gomuku.logic.MenuState

@Composable
fun MainMenu(modifier: Modifier, onClick: (MenuState) -> Unit) {
    Column(verticalArrangement = Arrangement.Center) {

        Button(onClick = { /*TODO*/ },modifier = modifier) {
            Text(text = "Play", fontSize = MENUBUTTONTEXTSIZE.sp)
        }
        Button(onClick = { onClick(MenuState.AUTHORS) },modifier = modifier ) {
            Text(text = "Authors", fontSize = MENUBUTTONTEXTSIZE.sp)
        }
        Button(onClick = { /*TODO*/ }, modifier = modifier) {
            Text(text = "Talk", fontSize = MENUBUTTONTEXTSIZE.sp)
        }
    }
}