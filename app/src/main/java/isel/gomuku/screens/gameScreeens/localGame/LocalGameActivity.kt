package isel.gomuku.screens.gameScreeens.localGame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import isel.gomuku.screens.component.BaseComponentActivity
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.components.DrawBoard
import isel.gomuku.ui.theme.GomukuTheme

class LocalGameActivity : BaseComponentActivity<LocalGameViewModel>() {

    override val viewModel: LocalGameViewModel by viewModels()

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
        safeSetContent {
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
                            val cellSize = (LocalConfiguration.current.screenWidthDp / viewModel.GRID_SIZE) - 1
                            DrawBoard(modifier = Modifier.padding(vertical = pad.calculateTopPadding()),
                                boardSize = viewModel.GRID_SIZE,
                                makePlay = { viewModel.play(it) },
                                moves = viewModel.getMoves(),
                                cellSize
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