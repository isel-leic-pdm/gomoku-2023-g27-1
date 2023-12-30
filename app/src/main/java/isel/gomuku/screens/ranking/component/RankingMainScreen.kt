package isel.gomuku.screens.ranking.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.R
import isel.gomuku.screens.ranking.RankingScreenState
import isel.gomuku.services.http.statistics.model.LeaderBoard
import isel.gomuku.services.http.statistics.model.PlayerStats
import isel.gomuku.utils.RANKING_TEXT_SIZE

import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    modifier: Modifier,
    onGetPlayer: (Int) -> Unit,
    onGetGlobalStatistics: () -> Unit,
    onGetRankings: () -> Unit,
    onGetMoreRankings: () -> Unit,
    onEditName: (String) -> Unit,
    playerStats:PlayerStats?,
    leaderBoard: LeaderBoard?,
    globalStatistics: GlobalStatistics?,
    currentState: RankingScreenState,
    nickname : String,
    searchMyRank: (LazyListState, CoroutineScope) -> Unit,
    searchNickname: (LazyListState, CoroutineScope) -> Unit,
) {

    when (currentState) {
        RankingScreenState.MENU ->
                RankingMenuScreen(
                    modifier = modifier,
                    onGetGlobalStatistics = onGetGlobalStatistics,
                    onGetRankings = onGetRankings,
                )
        RankingScreenState.LEADER_BOARD ->
            LeaderBoardScreen (
                modifier = modifier,
                onGetPlayer = onGetPlayer,
                onGetMoreRankings = onGetMoreRankings,
                onEditName = onEditName,
                nextPage = leaderBoard?.nextPage,
                bestPlayerRanking = leaderBoard?.players,
                nickname = nickname,
                searchMyRank = searchMyRank,
                searchNickname = searchNickname
        )

        RankingScreenState.GLOBAL_STATS ->
            GlobalStatsScreen(
            modifier = modifier,
            globalStatistics = globalStatistics
        )
        RankingScreenState.PLAYER_STATS ->
            PlayerStatsScreen(modifier = modifier, playerStats = playerStats )
    }

}


@Composable
fun RankingMenuScreen(
    modifier: Modifier,
    onGetGlobalStatistics: () -> Unit,
    onGetRankings: () -> Unit,
) {
    Row  {
        Spacer(Modifier.padding(16.dp))
        Column(modifier = modifier) {
            Button(
                onClick = { onGetRankings() },
            ) {
                Text(stringResource(id = R.string.ranking_leader_board), fontSize = RANKING_TEXT_SIZE.sp)
            }
            Spacer(Modifier.padding(16.dp))
            Button(
                onClick = { onGetGlobalStatistics() },
            ) {
                Text(stringResource(id = R.string.global_statistics), fontSize = RANKING_TEXT_SIZE.sp)
            }
        }
    }

}