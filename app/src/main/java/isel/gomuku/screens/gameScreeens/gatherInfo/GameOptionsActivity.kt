package isel.gomuku.screens.gameScreeens.gatherInfo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import isel.gomuku.screens.component.BaseComponentActivity
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.localGame.LocalGameActivity
import isel.gomuku.screens.users.UsersActivity
import isel.gomuku.screens.utils.viewModelInit
import isel.gomuku.ui.theme.GomukuTheme

class GameOptionsActivity : BaseComponentActivity<GameOptionsViewModel>() {


    override val viewModel: GameOptionsViewModel by viewModels {
        viewModelInit {
            GameOptionsViewModel(
                dependencyContainer.userService
            )
        }
    }

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
        if (viewModel.game.asGameStarted) {
            LocalGameActivity.navigate(this)
        }
        safeSetContent {
            GomukuTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBar(
                                navigationHandlers =
                                NavigationHandlers(
                                    onBackHandler = {
                                        finish()
                                    },
                                    navigateToLogin = { UsersActivity.navigate(this) }
                                    )
                            )
                        }) {}
                    SearchMatch(
                        viewModel.game,
                        { viewModel.changeGameType(it, { UsersActivity.navigate(this) }) },
                        viewModel::changeGridSize,
                        viewModel::changeOpeningRule,
                        viewModel::changeGameVariant
                    ) {
                        viewModel.startGame(this)
                    }
                }
            }
        }
    }
}