package isel.gomuku.screens.ranking

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.gomuku.utils.RANKING_TEXT_SIZE
import isel.gomuku.services.http.statistics.model.BestPlayerRanking


@Composable
fun RankingStateScreen(
    onBack: (RankingMenuState) -> Unit,
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
        RankingList(bestPlayerRanking = bestPlayerRanking)
    }
}



@Composable
fun RankingList(bestPlayerRanking: List<BestPlayerRanking>?) {
    LazyColumn(
        userScrollEnabled = true,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        bestPlayerRanking?.forEachIndexed { index, player ->
            items(1) {
               RankingRow(rank = index,  player = player)
            }
        }
    }
}

@Composable
fun RankingRow (rank :Int, player: BestPlayerRanking){
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
                Text(text = "#${rank + 1}", fontSize = RANKING_TEXT_SIZE.sp) //Rank
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
        currentRankingState = RankingMenuState.BEST_PLAYER,
        bestPlayerRanking = listOf(
            BestPlayerRanking("Player1", 50),
            BestPlayerRanking("Player2", 49),
            BestPlayerRanking("Player3", 48),
            BestPlayerRanking("Player4", 47),
            BestPlayerRanking("Player5", 46),
            BestPlayerRanking("Player6", 45),
            BestPlayerRanking("Player7", 44),
            BestPlayerRanking("Player8", 43),
            BestPlayerRanking("Player9", 42),
            BestPlayerRanking("Player10", 41),
            BestPlayerRanking("Player11", 40),
            BestPlayerRanking("Player12", 39),
            BestPlayerRanking("Player13", 38),
            BestPlayerRanking("Player14", 37),
            BestPlayerRanking("Player15", 36),
             BestPlayerRanking("Player16", 35),
            BestPlayerRanking("Player17", 34),
            BestPlayerRanking("Player18", 33),
            BestPlayerRanking("Player19", 32),
            BestPlayerRanking("Player20", 31)
        ))


}

