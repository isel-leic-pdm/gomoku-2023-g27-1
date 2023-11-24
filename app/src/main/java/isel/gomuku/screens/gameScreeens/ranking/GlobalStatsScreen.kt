package isel.gomuku.screens.gameScreeens.ranking

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.Idle
import isel.gomuku.helpers.LoadState
import isel.gomuku.helpers.Loaded
import isel.gomuku.helpers.Loading
import isel.gomuku.helpers.RANKING_TEXT_SIZE
import isel.gomuku.services.dto.GlobalStatistics


@Composable
fun GlobalStatsScreen(
    onBack: (RankingMenuState) -> Unit,
    globalStatistics: LoadState<GlobalStatistics> = Idle,
) {

    Column {
        Box {
            Button(onClick = { onBack(RankingMenuState.MENU) }) {
                Text(text = "Back to Rankings", fontSize = RANKING_TEXT_SIZE.sp)
            }
        }
        if (globalStatistics is Loaded) {
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
            globalStatistics.result.getOrNull()?.let { globalStats ->
                Text(text = "Total games: ${globalStats.totalGames}", fontSize = 25.sp)
                Text(text = "Total Victories: ${globalStats.totalVictories}", fontSize = 25.sp)
                Text(text = "Total Time: ${globalStats.totalTime}", fontSize = 25.sp)
            }

        }
    }
    if (globalStatistics is Loaded && globalStatistics.result.isFailure) {
        Log.v("GlobalStatsScreen", "Error: ${globalStatistics.result.exceptionOrNull()}")
    }
}



@Preview
@Composable
fun PreviewGlobalStatsScreen() {

}