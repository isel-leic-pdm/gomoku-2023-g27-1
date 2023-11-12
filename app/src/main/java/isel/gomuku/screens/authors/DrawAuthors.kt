package isel.gomuku.screens.authors

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.AUTHORS_TEXT_SIZE
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsScreen(backHandle: () -> Unit) {
    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = backHandle)) }){}

    Column {

        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
            Column(modifier = Modifier.fillMaxHeight(), Arrangement.SpaceEvenly) {
                Text(text = "48323 Simão Cabral", fontSize = AUTHORS_TEXT_SIZE.sp)
                Text(text = "49454 Eduardo Tavares", fontSize = AUTHORS_TEXT_SIZE.sp)
                Text(text = "48268 Marçorio Fortes", fontSize = AUTHORS_TEXT_SIZE.sp)
            }
        }
    }
}