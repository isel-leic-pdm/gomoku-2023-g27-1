package isel.gomuku.screens.ranking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.gomuku.GomokuApplication
import isel.gomuku.utils.MENU_BUTTON_WIDTH
import isel.gomuku.utils.MENU_PADDING
import isel.gomuku.screens.component.BaseComponentActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        safeSetContent {
            GomukuTheme {
                RankingScreen(
                    modifier = Modifier
                        .padding(MENU_PADDING.dp)
                        .width(MENU_BUTTON_WIDTH.dp),
                    onBack = { finish() },
                    onBackMenu ={viewModel.currentState = RankingMenuState.MENU},
                    onStats = viewModel::changeStatsToShow,
                    onGetGlobalStatistics = { viewModel.getGlobalStats() },
                    onGetRankings = { viewModel.getRankings() },
                    onGetMoreRankings = { viewModel.getMoreRankings() },
                    onEditName = viewModel::editName,
                    rankings = viewModel.rankings,
                    globalStatistics = viewModel.globalStatistics,
                    currentState = viewModel.currentState,
                    nickname = viewModel.nickname,
                    searchNickname = viewModel::search
                )
            }

        }
    }
}

