package isel.gomuku.screens.ranking

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isel.gomokuApi.domain.model.statistcs.BestPlayerRanking
import isel.gomuku.R
import isel.gomuku.utils.RANKING_TEXT_SIZE
import kotlinx.coroutines.CoroutineScope

const val NICKNAME_CHAR_LIMIT = 20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBoardScreen(
    modifier: Modifier,
    onState: (RankingScreenState) -> Unit,
    onGetPlayer: (Int) -> Unit,
    onGetMoreRankings: () -> Unit,
    onEditName: (String) -> Unit,
    nextPage: Int? = null,
    bestPlayerRanking: List<BestPlayerRanking>?,
    nickname: String,
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
            onState = onState,
            onGetPlayer = onGetPlayer,
            bestPlayerRanking = bestPlayerRanking,
            nextPage = nextPage, onEditName = onEditName,
            onGetMoreRankings = onGetMoreRankings,
            nickname = nickname,
            searchNickname = searchNickname
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingList(
    onState: (RankingScreenState) -> Unit,
    onGetPlayer: (Int) -> Unit,
    bestPlayerRanking: List<BestPlayerRanking>?,
    nextPage: Int?,
    onEditName: (String) -> Unit,
    onGetMoreRankings: () -> Unit,
    nickname: String,
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
            if (bestPlayerRanking != null) {
                this.items(bestPlayerRanking) {
                    RankingRow(onGetPlayer = onGetPlayer, onState= onState, player = it)
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
                onState: (RankingScreenState) -> Unit,
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


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun RankingStateScreenPreview() {
//    val listState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//    val f : (LazyListState, CoroutineScope) -> Unit
//    LeaderBoardScreen(
//        modifier = Modifier.padding(vertical = 16.dp),
//        onState = {},
//        onGetPlayer = {},
//        onGetMoreRankings = {},
//        onEditName = {},
//        nickname= "awd",
//        searchNickname =   ,
//        bestPlayerRanking = listOf(
//           BestPlayerRanking(1, "Player1", 100, 1),
//            BestPlayerRanking(2, "Player2", 90, 9),
//            BestPlayerRanking(3, "Player3", 80, 99),
//            BestPlayerRanking(4, "Player4", 70, 999),
//            BestPlayerRanking(5, "Player5", 60, 5),
//            BestPlayerRanking(6, "Player6", 50, 6),
//            BestPlayerRanking(7, "Player7", 40, 7),
//            BestPlayerRanking(8, "Player8", 30, 8),
//            BestPlayerRanking(9, "Player9", 20, 9),
//            BestPlayerRanking(10, "Player10", 10, 10),
//            BestPlayerRanking(11, "Player11", 0, 11),
//            BestPlayerRanking(12, "Player12", 0, 12),
//            BestPlayerRanking(13, "Player13", 0, 13),
//            BestPlayerRanking(14, "Player14", 0, 14),
//            BestPlayerRanking(15, "Player15", 0, 15),
//            BestPlayerRanking(16, "Player16", 0, 16),
//            BestPlayerRanking(17, "Player17", 0, 17),
//            BestPlayerRanking(18, "Player18", 0, 18),
//            BestPlayerRanking(19, "Player19", 0, 19),
//            BestPlayerRanking(20, "Player20", 0, 20),
//            BestPlayerRanking(21, "Player21", 0, 21),
//            BestPlayerRanking(22, "Player22", 0, 22),
//            BestPlayerRanking(23, "Player23", 0, 23),
//            BestPlayerRanking(24, "Player24", 0, 24),
//        ))
//
//
//}

