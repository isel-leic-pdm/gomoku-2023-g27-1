package isel.gomuku.screens.gameScreeens.ranking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.gomuku.helpers.MENU_BUTTON_WIDTH
import isel.gomuku.helpers.MENU_PADDING
import isel.gomuku.services.StatsServiceLocal
import isel.gomuku.ui.theme.GomukuTheme

enum class RankingMenuState {
    BEST_PLAYER,
    VICTORIES,
    DEFEATS,
    MOST_TIME,
    MOST_GAMES,
    GLOBAL_STATS,
    MENU
}

class RankingActivity : ComponentActivity() {
    private val rankingViewModel: RankingViewModel by viewModels()
    private val service = StatsServiceLocal()
    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, RankingActivity::class.java)
            source.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomukuTheme {
                when(rankingViewModel.currentState){
                    RankingMenuState.MENU ->  RankingScreen(
                        modifier = Modifier
                            .padding(MENU_PADDING.dp)
                            .width(MENU_BUTTON_WIDTH.dp),
                        onBack = { finish() },
                        onStats = rankingViewModel::changeStatsToShow,
                        onGetGlobalStatistics = { rankingViewModel.getGlobalStats(service)},
                        onGetRankings = {rankingViewModel.getRankings(service)}
                    )
                    RankingMenuState.BEST_PLAYER -> RankingStateScreen(
                        onBack = rankingViewModel::changeStatsToShow,
                        currentRankingState = rankingViewModel.currentState,
                        bestPlayers = rankingViewModel.rankings?.bestPlayerRanking
                    )
                    RankingMenuState.GLOBAL_STATS -> GlobalStatsScreen(
                        onBack = rankingViewModel::changeStatsToShow,
                        globalStatistics = rankingViewModel.globalStatistics
                    )
                    else -> {
                        TODO()
                    }
                }

            }
        }
    }

}