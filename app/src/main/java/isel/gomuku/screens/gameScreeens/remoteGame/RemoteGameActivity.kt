package isel.gomuku.screens.gameScreeens.remoteGame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import isel.gomuku.GomokuApplication
import isel.gomuku.screens.component.BaseComponentActivity
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.GameOptions
import isel.gomuku.screens.gameScreeens.components.DrawBoard
import isel.gomuku.ui.theme.GomukuTheme

class RemoteGameActivity : BaseComponentActivity<RemoteGameViewModel>() {

    private val app by lazy { application as GomokuApplication }

    companion object {
        private const val extra = "TO_CHANGE"
        fun navigate(source: ComponentActivity, options: GameOptions) {
            val intent = Intent(source, RemoteGameActivity::class.java)
            intent.putExtra(extra, options)
            source.startActivity(intent)
        }
    }

    override val viewModel: RemoteGameViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Test", "On create")
        super.onCreate(savedInstanceState)
        val gameOptions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.intent.getParcelableExtra(extra, GameOptions::class.java)
        else
            this.intent.getParcelableExtra<GameOptions?>(extra)
        require(gameOptions != null) { "How is it null?" }

        val token = app.userStorage.getUser()?.token

        viewModel.startGame(
            gameOptions.gridSize!!,
            gameOptions.variant!!,
            gameOptions.openingRule!!,
            app.gameService,
            token!!
        )

        safeSetContent {
            GomukuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        TopBar(navigationHandlers = NavigationHandlers(onBackHandler = {
                            finish()
                        }))
                    }) { pad ->
                        DrawBoard(
                            modifier = Modifier.padding(vertical = pad.calculateTopPadding()),
                            boardSize = gameOptions.gridSize,
                            makePlay = { viewModel.play(it, app.gameService, token) },
                            moves = viewModel.board.moves
                        )
                        Button(onClick = { viewModel.quit(app.gameService, token) }) {
                            Text("Give up")
                        }
                    }
                }
            }
        }
    }
}