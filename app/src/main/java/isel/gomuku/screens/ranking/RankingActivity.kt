package isel.gomuku.screens.ranking

import android.content.Intent
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
import androidx.compose.ui.Modifier
import isel.gomuku.GomokuApplication
import isel.gomuku.screens.component.BaseComponentActivity
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.users.UsersActivity
import isel.gomuku.screens.utils.viewModelInit
import isel.gomuku.ui.theme.GomukuTheme



class RankingActivity (): BaseComponentActivity<RankingViewModel> () {
    private val app by lazy { application as GomokuApplication }
    override val viewModel: RankingViewModel by viewModels {
        viewModelInit { RankingViewModel(app.statsService) }
    }

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, RankingActivity::class.java)
            source.startActivity(intent)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
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
                                onBackHandler = {
                                    when (viewModel.currentState) {
                                        RankingScreenState.MENU -> finish()
                                        RankingScreenState.PLAYER_STATS ->
                                            viewModel.currentState = RankingScreenState.LEADER_BOARD
                                        else -> viewModel.currentState = RankingScreenState.MENU
                                    }
                                                },
                            )
                        )
                    }) { pad ->
                        RankingScreen(
                            modifier = Modifier.padding(vertical = pad.calculateTopPadding()),
                            onState = viewModel::changeStatsToShow,
                            onGetPlayer = viewModel::getPlayerStats,
                            onGetGlobalStatistics = { viewModel.getGlobalStats() },
                            onGetRankings = { viewModel.getRankings() },
                            onGetMoreRankings = { viewModel.getMoreRankings() },
                            onEditName = viewModel::editName,
                            playerStats = viewModel.playerStats,
                            leaderBoard = viewModel.leaderBoard,
                            globalStatistics = viewModel.globalStatistics,
                            currentState = viewModel.currentState,
                            nickname = viewModel.nickname,
                            searchMyRank = {   ls, cs ->
                                viewModel.searchMyRank(ls, cs, redirect = { UsersActivity.navigate(this, it) } )
                                           },
                            searchNickname = viewModel::search
                        )
                    }

                }

            }
        }
    }
}

