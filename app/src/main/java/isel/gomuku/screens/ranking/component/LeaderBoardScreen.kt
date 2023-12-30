package isel.gomuku.screens.ranking.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isel.gomokuApi.domain.model.statistcs.BestPlayerRanking
import isel.gomuku.R
import isel.gomuku.screens.ranking.RankingScreenState
import isel.gomuku.utils.RANKING_TEXT_SIZE
import kotlinx.coroutines.CoroutineScope

const val NICKNAME_CHAR_LIMIT = 20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBoardScreen(
    modifier: Modifier,
    onGetPlayer: (Int) -> Unit,
    onGetMoreRankings: () -> Unit,
    onEditName: (String) -> Unit,
    nextPage: Int? = null,
    bestPlayerRanking: List<BestPlayerRanking>?,
    nickname: String,
    searchMyRank : (LazyListState, CoroutineScope) -> Unit,
    searchNickname: (LazyListState, CoroutineScope) -> Unit,

    ) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(text = "Leader Board", fontSize = RANKING_TEXT_SIZE.sp)
            }
        }
        RankingList(
            onGetPlayer = onGetPlayer,
            bestPlayerRanking = bestPlayerRanking,
            nextPage = nextPage, onEditName = onEditName,
            onGetMoreRankings = onGetMoreRankings,
            nickname = nickname,
            searchMyRank = searchMyRank,
            searchNickname = searchNickname
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingList(
    onGetPlayer: (Int) -> Unit,
    bestPlayerRanking: List<BestPlayerRanking>?,
    nextPage: Int?,
    onEditName: (String) -> Unit,
    onGetMoreRankings: () -> Unit,
    nickname: String,
    searchMyRank: (LazyListState, CoroutineScope) -> Unit,
    searchNickname: (LazyListState, CoroutineScope) -> Unit,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = nickname,
                onValueChange = {
                    if (it.length <= NICKNAME_CHAR_LIMIT)
                        onEditName(it)
                },
                label = { Text(stringResource(id = R.string.nickname)) },
                modifier = Modifier
                    .padding(8.dp),
                singleLine = true,
            )
            IconButton(
                onClick = {
                    searchNickname(listState, coroutineScope)
                },
                modifier = Modifier
                    .padding(8.dp)
                ,
                enabled = nickname.isNotBlank()

            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Button",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        LazyColumn(
            state = listState,
            userScrollEnabled = true,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Button(onClick = { searchMyRank(listState, coroutineScope) }) {
                    Text(text = stringResource(id = R.string.search_my_ranking), fontSize = RANKING_TEXT_SIZE.sp)
                }
            }
            if (bestPlayerRanking != null) {
                this.items(bestPlayerRanking) {
                    RankingRow(onGetPlayer = onGetPlayer, player = it)
                }
            }
            item {
                Button(onClick = { onGetMoreRankings() }, enabled = nextPage != null) {
                    Text(text = stringResource(id = R.string.load_more), fontSize = RANKING_TEXT_SIZE.sp)
                }
            }
        }
    }
}


@Composable
fun RankingRow(
    onGetPlayer: (Int) -> Unit,
    player: BestPlayerRanking) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onGetPlayer(player.id) }
    ) {

        Column(
            modifier = Modifier.padding(4.dp)
        ) {

            Text(
                text = "Rank: ${player.rank}",
                fontSize = RANKING_TEXT_SIZE.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 4.dp),
            ) //Rank

            Text(
                text = "Nickname: ${player.playerName}",
                fontSize = RANKING_TEXT_SIZE.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 4.dp),
            ) //Rank

            Text(
                text = "Points: ${player.points}",
                fontSize = RANKING_TEXT_SIZE.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 4.dp),
            ) //Rank

        }
    }

}



