package isel.gomuku.screens.gameScreeens.localGame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import isel.gomuku.MainActivity
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.screens.authors.AuthorsScreenActivity
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.components.DrawBoard
import isel.gomuku.screens.gameScreeens.gatherInfo.GameOptionsActivity
import isel.gomuku.ui.theme.GomukuTheme

class LocalGameActivity : ComponentActivity() {

    private val localGame: LocalGameViewModel by viewModels()

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, LocalGameActivity::class.java)
            source.startActivity(intent)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomukuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                        Scaffold(topBar = {
                            TopBar(
                                navigationHandlers = NavigationHandlers(
                                    onBackHandler = { finish() })
                            )
                        }) { pad ->
                            DrawBoard(modifier = Modifier.padding(vertical = pad.calculateTopPadding()),
                                boardSize = localGame.GRID_SIZE,
                                makePlay = { localGame.play(it) },
                                moves = localGame.getMoves()
                            )
                        }
                }

            }
        }
    }

    override fun onBackPressed() {
        this.finish()
        TODO("Player will quit game")
    }
}