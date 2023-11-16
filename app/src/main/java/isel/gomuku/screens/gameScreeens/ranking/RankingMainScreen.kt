package isel.gomuku.screens.gameScreeens.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.service.dto.GlobalStatistics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    modifier: Modifier,
    onBack: () -> Unit,
    onStats: (RankingMenuState) -> Unit,
    onGetGlobalStatistics: () -> Unit,
    onGetRankings: () ->  Unit
) {
    Scaffold(topBar = { TopBar(navigationHandlers = NavigationHandlers(onBackHandler = onBack)) })
    { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            Button(onClick =  { onGetRankings() ; onStats (RankingMenuState.BEST_PLAYER) }, modifier = modifier) {
                Text(text = "Best Players Ranking")
            }
            Button(onClick =  { onGetGlobalStatistics() ; onStats (RankingMenuState.GLOBAL_STATS) }, modifier = modifier) {
                Text(text = "Global Statistics")
            }
        }
    }
}
