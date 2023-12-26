package isel.gomuku.screens.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.utils.RANKING_TEXT_SIZE


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
                .padding(16.dp)
                .background(color = Color.Blue,  shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
            ) {
                Text(text = "Global statistics", fontSize = 24.sp)
            }
        }
        Column ( modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ) {
            ColumnItem ("Games", "${globalStatistics?.totalGames}")
            ColumnItem ("Victories", "${globalStatistics?.totalVictories}")
            ColumnItem ("Time Played", "${globalStatistics?.totalTime}")
        }
    }
}

@Composable
fun ColumnItem(description: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = "$description :", color = Color.Black, fontSize = 24.sp)
        }
        Box(
            modifier = Modifier
                .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = value, color = Color.Black, fontSize = 24.sp)
        }
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