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
import isel.gomuku.helpers.Idle
import isel.gomuku.helpers.LoadState
import isel.gomuku.helpers.MENU_BUTTON_TEXT_SIZE
import isel.gomuku.helpers.RANKING_TEXT_SIZE

import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.services.dto.GlobalStatistics
import isel.gomuku.services.dto.Rankings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    onStats: (RankingMenuState) -> Unit,
    onGetGlobalStatistics: () -> Unit,
    onGetRankings: () -> Unit,
    rankings:LoadState<Rankings> = Idle,
    globalStatistics: LoadState<GlobalStatistics> = Idle,
    currentState: RankingMenuState
) {
    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = onBack)) })
    { paddingValues ->
        when (currentState) {
            RankingMenuState.MENU -> RankingMenuScreen(
                modifier = modifier,
                onStats = onStats,
                onGetGlobalStatistics = onGetGlobalStatistics,
                onGetRankings = onGetRankings,
                paddingValues = paddingValues
            )

            RankingMenuState.BEST_PLAYER -> RankingStateScreen(
                onBack = onStats,
                currentRankingState = currentState,
                rankings = rankings
            )

            RankingMenuState.GLOBAL_STATS -> GlobalStatsScreen(
                onBack = onStats,
                globalStatistics = globalStatistics
            )

            else -> TODO()
        }
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
    Column (modifier = modifier.padding(paddingValues)) {
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