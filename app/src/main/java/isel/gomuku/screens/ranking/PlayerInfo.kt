package isel.gomuku.screens.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import isel.gomuku.services.http.statistics.model.PlayerStats


@Composable
fun  PlayerStatsScreen (onClick: (Int) -> Unit, playerStats: PlayerStats ) {

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ColumnItem(description = "Nickname", value = playerStats.nickname )
        ColumnItem(description = "Points", value = "${playerStats.points}")
        ColumnItem(description = "Victories", value = "${playerStats.victories}")
        ColumnItem(description = "Defeats", value = "${playerStats.defeats}")
        ColumnItem(description = "Draws", value ="${playerStats.draws}")
        ColumnItem(description = "Games Played", value ="${playerStats.gamesPlayed}")
        ColumnItem(description = "Time Played", value = "${playerStats.timePlayed}")
    }
}

@Preview
@Composable
fun PreviewPlayerStatsScreen (){
    val playerStats  = PlayerStats (
        nickname = "marsul",
        points = 150,
        victories = 10,
        defeats = 1,
        draws = 4,
        gamesPlayed = 15,
        timePlayed = "4h 10m 58s"
    )
    PlayerStatsScreen(onClick = {}, playerStats = playerStats )
}
