package isel.gomuku.screens.ranking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.R
import isel.gomuku.screens.component.ColumnItem
import isel.gomuku.utils.RANKING_TEXT_SIZE


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalStatsScreen(
    modifier: Modifier,
    globalStatistics: GlobalStatistics?,
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
                    Text(text = stringResource (id = R.string.global_statistics), fontSize = RANKING_TEXT_SIZE.sp)
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ColumnItem(stringResource (id = R.string.stats_games), "${globalStatistics?.totalGames}")
                ColumnItem(stringResource (id = R.string.stats_victories), "${globalStatistics?.totalVictories}")
                ColumnItem(stringResource (id = R.string.stats_time), "${globalStatistics?.totalTime}")
            }
        }
}

//@Preview
//@Composable
//fun PreviewGlobalStatsScreen() {
//    GlobalStatsScreen(
//        onBack = {},
//        globalStatistics = GlobalStatistics("10:20:30", 2, 3)
//    )
//}