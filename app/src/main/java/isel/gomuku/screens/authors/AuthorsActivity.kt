package isel.gomuku.screens.authors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import isel.gomuku.ui.theme.GomukuTheme

class AuthorsScreenActivity: ComponentActivity(){

    companion object{
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, AuthorsScreenActivity::class.java)
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
                    AuthorsScreen{this.finish()}
                }
            }
        }
    }

}