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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.utils.RANKING_TEXT_SIZE


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalStatsScreen(
    onBack: () -> Unit,
    globalStatistics: GlobalStatistics?,
) {
    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = onBack)) })
    { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
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
                    Text(text = "Global statistics", fontSize = RANKING_TEXT_SIZE.sp)
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ColumnItem("Games Played", "${globalStatistics?.totalGames}")
                ColumnItem("Victories", "${globalStatistics?.totalVictories}")
                ColumnItem("Time Played", "${globalStatistics?.totalTime}")
            }
        }
    }
}

@Composable
fun ColumnItem(description: String, value: String) {

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor =MaterialTheme.colorScheme.primaryContainer
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(text = description,
                fontSize = RANKING_TEXT_SIZE.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp)
            )

            Text(text = value,
                fontSize = RANKING_TEXT_SIZE.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp)
            )

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