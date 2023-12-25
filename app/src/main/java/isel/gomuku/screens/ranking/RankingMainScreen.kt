package isel.gomuku.screens.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.utils.RANKING_TEXT_SIZE

import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.services.http.statistics.model.RankingModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    onStats: (RankingMenuState) -> Unit,
    onGetGlobalStatistics: () -> Unit,
    onGetRankings: () -> Unit,
    rankings: RankingModel?,
    globalStatistics: GlobalStatistics?,
    currentState: RankingMenuState
) {

    when (currentState) {

        RankingMenuState.MENU ->
            Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = onBack)) })
            { paddingValues ->
                RankingMenuScreen(
                    modifier = modifier,
                    onStats = onStats,
                    onGetGlobalStatistics = onGetGlobalStatistics,
                    onGetRankings = onGetRankings,
                    paddingValues = paddingValues
                )
            }

        RankingMenuState.BEST_PLAYER -> RankingStateScreen (
            onBack = onStats,
            onGetRankings = onGetRankings,
            nextPage = rankings?.nextPage,
            currentRankingState = currentState,
            bestPlayerRanking = rankings?.rankings?.bestPlayers
        )

        RankingMenuState.GLOBAL_STATS -> GlobalStatsScreen(
            onBack = onStats,
            globalStatistics = globalStatistics
        )
    }

}


@Composable
fun RankingMenuScreen(
    modifier: Modifier,
    onStats: (RankingMenuState) -> Unit,
    onGetGlobalStatistics: () -> Unit,
    onGetRankings: () -> Unit,
    paddingValues: PaddingValues
) {
    Column(modifier = modifier.padding(paddingValues)) {
        Button(
            onClick = { onGetRankings(); onStats(RankingMenuState.BEST_PLAYER) },
            modifier = modifier
        ) {
            Text(text = "Best Players Ranking", fontSize = RANKING_TEXT_SIZE.sp)
        }
        Button(
            onClick = { onGetGlobalStatistics(); onStats(RankingMenuState.GLOBAL_STATS) },
            modifier = modifier
        ) {
            Text(text = "Global Statistics", fontSize = RANKING_TEXT_SIZE.sp)
        }
    }
}