package isel.gomuku.screens.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import isel.gomuku.httpServices.model.statistics.GlobalStatistics


@Composable
fun GlobalStatsScreen(
    onBack: (RankingMenuState) -> Unit,
    globalStatistics: GlobalStatistics?,
) {

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
                Text(text = "Global statistics", fontSize = 30.sp)
            }
        }

        Text(text = "Total games: ${globalStatistics?.totalGames}", fontSize = 25.sp)
        Text(text = "Total Victories: ${globalStatistics?.totalVictories}", fontSize = 25.sp)
        Text(text = "Total Time: ${globalStatistics?.totalTime}", fontSize = 25.sp)
    }
}


@Preview
@Composable
fun PreviewGlobalStatsScreen() {
    GlobalStatsScreen(
        onBack = {},
        globalStatistics = GlobalStatistics("10:20:30", 2, 3)
    )
}