package isel.gomuku.screens.gameScreeens.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.RANKING_TEXT_SIZE
import isel.gomuku.services.dto.BestPlayerRanking


@Composable
fun RankingStateScreen(
    onBack: (RankingMenuState) -> Unit,
    currentRankingState: RankingMenuState,
    bestPlayers: List<BestPlayerRanking>?
) {
    val (description, tittle) = selectRankingState(currentRankingState) ?: return


    Column {
        Box {
            Button(onClick = { onBack(RankingMenuState.MENU) }) {
                Text(text = "Back to Rankings", fontSize = RANKING_TEXT_SIZE.sp)
            }
        }

        Row(
            modifier = Modifier
                .padding(5.dp)
                .background(color = Color.Blue)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
            ) {
                Text(text = description, fontSize = 30.sp)
            }

        }
        bestPlayers?.forEachIndexed { index, player ->
            if (index == 0) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(color = Color.Blue)
                        .height(30.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
                    ) {
                        Text(text = "Rank", fontSize = 25.sp) //Rank
                    }
                    Box(
                        modifier = Modifier
                            .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
                    ) {
                        Text(text = "Player", fontSize = 25.sp) //Rank
                    }

                    Box(
                        modifier = Modifier
                            .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
                    ) {
                        Text(text = tittle, fontSize = 25.sp) //Rank
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = Color.Blue)
                    .height(30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .padding()
                        .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
                ) {
                    Text(text = "${index + 1}", fontSize = 25.sp) //Rank
                }
                Box(
                    modifier = Modifier
                        .padding()
                        .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
                ) {
                    Text(text = player.playerName, fontSize = 25.sp) //Rank
                }

                Box(
                    modifier = Modifier
                        .padding()
                        .background(color = Color.Yellow, shape = RoundedCornerShape(5.dp))
                ) {
                    Text(text = "${player.points}", fontSize = 25.sp) //Rank
                }
            }
        }

    }
}


fun selectRankingState(currentRankingState: RankingMenuState): Pair<String, String>? {
    return when (currentRankingState) {
        RankingMenuState.BEST_PLAYER -> Pair("Best Players Ranking", "Points")
        RankingMenuState.DEFEATS -> Pair("Defeats Ranking", "Defeats")
        RankingMenuState.VICTORIES -> Pair("Victories Ranking", "Victories")
        RankingMenuState.MOST_TIME -> Pair("Time played Ranking", "Time")
        RankingMenuState.MOST_GAMES -> Pair("Games Played Ranking", "Games")
        else -> null
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RankingStateScreenPreview() {

}

