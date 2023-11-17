package isel.gomuku.screens.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.gomuku.R
import isel.gomuku.helpers.ERROR_MESSAGE_TEXT_SIZE

@Composable
fun ErrorMessage(error: String,resetError : () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = 0.5f))
    )
    {
        Box(modifier = Modifier.align(Alignment.Center).background(Color.Gray.copy(alpha = 0.8f))){
            Text(
                text = error,
                modifier = Modifier.align(Alignment.Center),
                fontSize = ERROR_MESSAGE_TEXT_SIZE.sp
            )
        }
        Button(
            onClick = {
                resetError()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ) {
            Text(text = stringResource(id = R.string.Dismiss))
        }
    }
}
