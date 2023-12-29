package isel.gomuku.screens.gameScreeens.gatherInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.gomuku.R
import isel.gomuku.screens.gameScreeens.GameOptions

enum class GridSize(val size : Int){
    SIZE_15(15),SIZE_19(19)
}
enum class OpeningRules{
    STANDARD
}
enum class GameVariants{
    STANDARD
}

@Composable
fun SearchMatch(
    buildGameOptions: GameOptions,
    changeGameType : (Boolean) -> Unit,
    changeGridSize : (GridSize) -> Unit,
    changeOpeningRule : (OpeningRules) -> Unit,
    changeGameVariant : (GameVariants) -> Unit,
    startGame : () -> Unit
    ) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.game_type))
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = { changeGameType(false) },
                        enabled = buildGameOptions.isGameLocal
                    ) {
                        Text(text = stringResource(id = R.string.remote_game_title))
                    }
                    Button(
                        onClick = { changeGameType(true) },
                        enabled = !buildGameOptions.isGameLocal
                    ) {
                        Text(text = stringResource(id = R.string.local_game_title))
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.choose_grid))
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    GridSize.values().forEach {
                        Button(
                            onClick = { changeGridSize(it) },
                            enabled = (buildGameOptions.gridSize != it.size) && !buildGameOptions.isGameLocal
                        ) {
                            Text(text = it.size.toString())
                        }
                    }
                }
                Row {
                    Text(text = stringResource(id = R.string.choose_open_rule))
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = { changeOpeningRule(OpeningRules.STANDARD) },
                        enabled = (buildGameOptions.openingRule != OpeningRules.STANDARD) && !buildGameOptions.isGameLocal
                    ) {

                        Text(text = OpeningRules.STANDARD.name)
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = stringResource(id = R.string.choose_game_variant))
                }
                Row {
                    Button(
                        onClick = { changeGameVariant(GameVariants.STANDARD) },
                        enabled = (buildGameOptions.variant != GameVariants.STANDARD) && !buildGameOptions.isGameLocal
                    ) {
                        Text(text = GameVariants.STANDARD.name)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { startGame() }, enabled = buildGameOptions.canStartGame) {
                    Text(text = stringResource(id = R.string.start_game))
                }
            }
        }
    }
}