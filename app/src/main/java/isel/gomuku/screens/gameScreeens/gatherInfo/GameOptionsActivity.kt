package isel.gomuku.screens.gameScreeens.gatherInfo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.localGame.LocalGameActivity
import isel.gomuku.screens.gameScreeens.remoteGame.RemoteGameActivity
import isel.gomuku.ui.theme.GomukuTheme

class GameOptionsActivity : ComponentActivity() {


    private val gatherInfoViewModel : GameOptionsViewModel by viewModels()

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, GameOptionsActivity::class.java)
            source.startActivity(intent)
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(gatherInfoViewModel.game.asGameStarted){
            LocalGameActivity.navigate(this)
        }
        setContent {
            GomukuTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = { finish() })) }) {
                    }
                    SearchMatch(
                        gatherInfoViewModel.game,
                        gatherInfoViewModel::changeGameType,
                        gatherInfoViewModel::changeGridSize,
                        gatherInfoViewModel::changeOpeningRule,
                        gatherInfoViewModel::changeGameVariant
                    ) {
                        if (gatherInfoViewModel.game.isGameLocal) {
                            gatherInfoViewModel.game.startGame()
                            LocalGameActivity.navigate(this)
                        }
                        RemoteGameActivity.navigate(this, gatherInfoViewModel.game)
                    }
                }
            }
        }
    }
}