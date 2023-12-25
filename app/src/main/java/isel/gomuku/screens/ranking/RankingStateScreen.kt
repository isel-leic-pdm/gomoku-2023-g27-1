package isel.gomuku.screens.ranking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isel.gomokuApi.domain.model.statistcs.BestPlayerRanking
import isel.gomuku.utils.RANKING_TEXT_SIZE


@Composable
fun RankingStateScreen(
    onBack: (RankingMenuState) -> Unit,
    onGetRankings : () -> Unit,
    nextPage : String? = null,
    currentRankingState: RankingMenuState,
    bestPlayerRanking: List<BestPlayerRanking>?
) {

    Column {
        Box {
            Button(onClick = { onBack(RankingMenuState.MENU) }) {
                Text(text = "Back to Rankings", fontSize = RANKING_TEXT_SIZE.sp)
            }
        }

        Row(
            modifier = Modifier
                .background(color = Color.Blue, shape = RoundedCornerShape(5.dp))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
            ) {
                Text(text = "Leader Board", fontSize = RANKING_TEXT_SIZE.sp)
            }
        }
        RankingList(bestPlayerRanking = bestPlayerRanking, nextPage = nextPage, onGetRankings = onGetRankings)
    }
}



@Composable
fun RankingList(bestPlayerRanking: List<BestPlayerRanking>?, nextPage: String?, onGetRankings: () -> Unit) {
    LazyColumn(
        userScrollEnabled = true,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Log.d("RankingList", "RankingList: ${bestPlayerRanking?.size}")
        bestPlayerRanking?.forEach { player ->
            items(1) {
               RankingRow(  player = player)
            }
        }
    }
}

@Composable
fun RankingRow ( player: BestPlayerRanking){
    Row(
        modifier = Modifier
            .background(color = Color.Blue, shape = RoundedCornerShape(5.dp))
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
            ) {
                Text(text = "#${player.rank}", fontSize = RANKING_TEXT_SIZE.sp) //Rank
            }
            Box(
                modifier = Modifier
                    .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
            ) {
                Text(text = player.playerName, fontSize = RANKING_TEXT_SIZE.sp) //Rank
            }

            Box(
                modifier = Modifier
                    .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
            ) {
                Text(text = "P${player.points}", fontSize = RANKING_TEXT_SIZE.sp) //Rank
            }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RankingStateScreenPreview() {
RankingStateScreen(
        onBack = {},
        onGetRankings = {},
        currentRankingState = RankingMenuState.BEST_PLAYER,
        bestPlayerRanking = listOf(
           BestPlayerRanking(1, "Player1", 100, 1),
            BestPlayerRanking(2, "Player2", 90, 2),
            BestPlayerRanking(3, "Player3", 80, 3),
            BestPlayerRanking(4, "Player4", 70, 4),
            BestPlayerRanking(5, "Player5", 60, 5),
            BestPlayerRanking(6, "Player6", 50, 6),
            BestPlayerRanking(7, "Player7", 40, 7),
            BestPlayerRanking(8, "Player8", 30, 8),
            BestPlayerRanking(9, "Player9", 20, 9),
            BestPlayerRanking(10, "Player10", 10, 10),
            BestPlayerRanking(11, "Player11", 0, 11),
            BestPlayerRanking(12, "Player12", 0, 12),
            BestPlayerRanking(13, "Player13", 0, 13),
            BestPlayerRanking(14, "Player14", 0, 14),
            BestPlayerRanking(15, "Player15", 0, 15),
            BestPlayerRanking(16, "Player16", 0, 16),
            BestPlayerRanking(17, "Player17", 0, 17),
            BestPlayerRanking(18, "Player18", 0, 18),
            BestPlayerRanking(19, "Player19", 0, 19),
            BestPlayerRanking(20, "Player20", 0, 20),
            BestPlayerRanking(21, "Player21", 0, 21),
            BestPlayerRanking(22, "Player22", 0, 22),
            BestPlayerRanking(23, "Player23", 0, 23),
            BestPlayerRanking(24, "Player24", 0, 24),
        ))


}

