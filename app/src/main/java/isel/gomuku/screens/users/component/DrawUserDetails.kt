package isel.gomuku.screens.users.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import isel.gomuku.repository.user.model.LoggedUser

@OptIn(ExperimentalTextApi::class)
@Composable
fun DrawUserDetails(
    modifier: Modifier,
    user: LoggedUser
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome:", fontWeight = FontWeight.Bold)
            Text(
                text = user.name,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Green,
                            Color.Blue,
                            Color.Magenta
                        )
                    )
                )
            )
        }
    }
}