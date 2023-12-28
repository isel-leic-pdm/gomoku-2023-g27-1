package isel.gomuku.screens.authors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import isel.gomuku.utils.AUTHORS_TEXT_SIZE
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsScreen(backHandle: () -> Unit, sendEmail: (String) -> Unit) {
    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = backHandle)) }) { pad ->
        Column(Modifier.padding(vertical = pad.calculateTopPadding())) {

            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                Column(modifier = Modifier.fillMaxHeight(), Arrangement.SpaceEvenly) {
                    Text(text = "48323 Simão Cabral", fontSize = AUTHORS_TEXT_SIZE.sp)
                    Text(text = "49454 Eduardo Tavares", fontSize = AUTHORS_TEXT_SIZE.sp)
                    Text(text = "48268 Marçorio Fortes", fontSize = AUTHORS_TEXT_SIZE.sp)
                    SendEmail(sendEmail = sendEmail, modifier = Modifier)
                }
            }

        }

    }


}

@Composable
fun SendEmail(sendEmail: (String) -> Unit, modifier: Modifier) {
    Button(onClick = { sendEmail("A49454@alunos.isel.pt,A48268@alunos.isel.pt,A48323@alunos.isel.pt") }, modifier) {
        Text(text = "Send Email", fontSize = AUTHORS_TEXT_SIZE.sp)
    }
}