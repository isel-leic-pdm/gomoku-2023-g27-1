package isel.gomuku.screens.gameScreeens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.gomuku.R

@Composable
fun EndingScreen(winner: Boolean?,) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val text: String = when (winner) {
            null -> stringResource(R.string.Tie)
            true -> stringResource(R.string.Winner)
            false -> stringResource(R.string.Loser)
        }
        Box(modifier = Modifier.background(Color.LightGray)){
            Text(text = text)
        }

    }
}