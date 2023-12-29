package isel.gomuku.screens.gameScreeens.remoteGame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import isel.gomuku.screens.component.BaseComponentActivity
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.GameOptions
import isel.gomuku.screens.gameScreeens.components.EndingScreen
import isel.gomuku.screens.gameScreeens.remoteGame.component.DrawRemoteGame
import isel.gomuku.screens.users.UsersActivity
import isel.gomuku.screens.utils.viewModelInitWithSavedState
import isel.gomuku.ui.theme.GomukuTheme
import kotlinx.coroutines.delay

class RemoteGameActivity : BaseComponentActivity<RemoteGameViewModel>() {


    companion object {
        private const val extra = "TO_CHANGE"
        fun navigate(source: ComponentActivity, options: GameOptions) {
            val intent = Intent(source, RemoteGameActivity::class.java)
            intent.putExtra(extra, options)
            source.startActivity(intent)
        }
    }

    override val viewModel: RemoteGameViewModel by viewModels {
        viewModelInitWithSavedState(this) {
            RemoteGameViewModel(
                it,
                dependencyContainer.userService,
                dependencyContainer.gameService
            )
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        Log.d("Test", "Resumed2.0")
        super.onActivityReenter(resultCode, data)
    }

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




        safeSetContent {
            viewModel.startGame(
                gameOptions.gridSize!!,
                gameOptions.variant!!,
                gameOptions.openingRule!!,
                { UsersActivity.navigate(this, it) }
            )
            GomukuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        TopBar(navigationHandlers = NavigationHandlers(onBackHandler = {
                            finish()
                        }))
                    }) { pad ->
                        LaunchedEffect(viewModel.poll) {
                            while (viewModel.poll.isPolling) {
                                delay(2000)
                                viewModel.fetchState {
                                    UsersActivity.navigate(
                                        this@RemoteGameActivity,
                                        it
                                    )
                                }
                            }

                        }
                        DrawRemoteGame(
                            modifier = Modifier.padding(vertical = pad.calculateTopPadding()),
                            gridSize = gameOptions.gridSize,
                            makePlay = {
                                if (!viewModel.isGameOver) {
                                    viewModel.play(it, { UsersActivity.navigate(this, it) })
                                }
                            },
                            moves = viewModel.moves,
                            quit = {
                                if (!viewModel.isGameOver) {
                                    viewModel.quit({
                                        UsersActivity.navigate(
                                            this,
                                            it
                                        )
                                    }) { this.finish() }
                                }
                            },
                            viewModel.poll,
                            viewModel.player,
                            viewModel.opponent
                        )
                        if (viewModel.isGameOver) {
                            EndingScreen(viewModel.winner)
                        }
                    }
                }
            }
        }
    }
}
