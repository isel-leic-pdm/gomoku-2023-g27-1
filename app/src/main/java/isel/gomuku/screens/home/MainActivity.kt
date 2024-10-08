package isel.gomuku.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import isel.gomuku.utils.MENU_BUTTON_WIDTH
import isel.gomuku.utils.MENU_PADDING
import isel.gomuku.screens.authors.AuthorsScreenActivity
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.gameScreeens.gatherInfo.GameOptionsActivity
import isel.gomuku.screens.ranking.RankingActivity
import isel.gomuku.screens.users.UsersActivity
import isel.gomuku.ui.theme.GomukuTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, MainActivity::class.java)
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
                    //Log.d("Test1",userToken ?: "NotLogged")
                    MainMenu(
                        Modifier
                            .padding(MENU_PADDING.dp)
                            .width(MENU_BUTTON_WIDTH.dp),
                        playHandle = { GameOptionsActivity.navigate(this)},
                        authorsHandler = { AuthorsScreenActivity.navigate(this) },
                        rankingHandle = { RankingActivity.navigate(this)},
                        loginHandler = { UsersActivity.navigate(this)}

                    )

                }
            }
        }
    }

    override fun onBackPressed() {
        this.finish()
        exitProcess(0)
        TODO("Incorrect screen navigation")
    }
}

class EmptyViewModel : BaseViewModel() {

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    GomukuTheme {
        /*GameBoard({}) {

        }*/
    }
}