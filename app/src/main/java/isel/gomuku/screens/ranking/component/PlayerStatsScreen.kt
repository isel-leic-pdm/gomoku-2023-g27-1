package isel.gomuku.screens.ranking.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import isel.gomuku.R
import isel.gomuku.screens.component.ColumnItem
import isel.gomuku.services.http.statistics.model.PlayerStats


@Composable
fun  PlayerStatsScreen (modifier: Modifier, playerStats: PlayerStats? ) {

    Row (
        modifier = modifier,
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            if (playerStats != null) {
                ColumnItem(stringResource(id = R.string.player_nickname), value = playerStats.nickname )
                ColumnItem(stringResource(id = R.string.player_points), value = "${playerStats.points}")
                ColumnItem(stringResource(id = R.string.player_victories), value = "${playerStats.victories}")
                ColumnItem(stringResource(id = R.string.player_defeats), value = "${playerStats.defeats}")
                ColumnItem(stringResource(id = R.string.player_draws), value ="${playerStats.draws}")
                ColumnItem(stringResource(id = R.string.player_defeats), value ="${playerStats.gamesPlayed}")
                ColumnItem(stringResource(id = R.string.player_time), value = playerStats.timePlayed)
            }
        }

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
    PlayerStatsScreen( modifier = Modifier.padding(16.dp), playerStats = playerStats )
}
