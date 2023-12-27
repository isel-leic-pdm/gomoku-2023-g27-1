package isel.gomuku.screens.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    onBackMenu: () -> Unit,
    onStats: (RankingMenuState) -> Unit,
    onGetGlobalStatistics: () -> Unit,
    onGetRankings: () -> Unit,
    onGetMoreRankings: () -> Unit,
    onEditName: (String) -> Unit,
    rankings: RankingModel?,
    globalStatistics: GlobalStatistics?,
    currentState: RankingMenuState,
    nickname : String,
    searchNickname: (LazyListState, CoroutineScope) -> Unit,
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

        RankingMenuState.BEST_PLAYER ->
            LeaderBoardScreen (
            onBack =  onBackMenu ,
            onGetMoreRankings = onGetMoreRankings,
            onEditName = onEditName,
            nextPage = rankings?.nextPage,
            bestPlayerRanking = rankings?.rankings?.bestPlayers,
            nickname = nickname,
            searchNickname = searchNickname
        )

        RankingMenuState.GLOBAL_STATS -> GlobalStatsScreen(
            onBack = onBackMenu,
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