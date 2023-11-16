package isel.gomuku.screens.gameScreeens.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import isel.gomuku.helpers.MENU_BUTTON_TEXT_SIZE
import isel.gomuku.services.dto.BestPlayerRanking

@Composable
fun RankingStateScreen (
                        onBack: (RankingMenuState) -> Unit,
                        bestPlayers : List<BestPlayerRanking>?
                        )
{
    Column(verticalArrangement = Arrangement.Center) {
        Box {
            Button(onClick = {onBack(RankingMenuState.MENU)} ) {
                Text(text = "Back to Ranking Menu", fontSize =  MENU_BUTTON_TEXT_SIZE.sp)
            }
        }
        bestPlayers?.forEachIndexed { index, player ->
            Box {
                Text(text = "Rank: ${index+1} Player Name:${player.playerName} - Points: ${player.points}",
                    fontSize =  MENU_BUTTON_TEXT_SIZE.sp)
            }
        }
    }
}