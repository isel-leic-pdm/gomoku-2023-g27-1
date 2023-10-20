package isel.gomuku.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.AUTHORS_TEXT_SIZE
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.ui.theme.GomukuTheme


class AuthorsScreen: ComponentActivity(){

    companion object{
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, AuthorsScreen::class.java)
            source.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GomukuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Biography{this.finish()}
                }
            }
        }



    }

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Biography(backHandle: () -> Unit) {
    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = backHandle)) }){}

    Column {

        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
            Column(modifier = Modifier.fillMaxHeight(), Arrangement.SpaceEvenly) {
                Text(text = "48323 Simão Cabral", fontSize = AUTHORS_TEXT_SIZE.sp)
                Text(text = "49454 Eduardo Tavares", fontSize = AUTHORS_TEXT_SIZE.sp)
                Text(text = "XXXXX Marçorio Fortes", fontSize = AUTHORS_TEXT_SIZE.sp)
            }
        }
    }
}