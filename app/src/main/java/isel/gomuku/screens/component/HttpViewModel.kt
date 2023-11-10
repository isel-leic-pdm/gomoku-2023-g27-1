package isel.gomuku.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.gomuku.R
import kotlinx.coroutines.launch

abstract class HttpViewModel : ViewModel() {
    var waiting by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun request(httpRequest: suspend () -> Unit) {
        waiting = true

        viewModelScope.launch {
            try {
                //adicionar pedido http
                httpRequest()
            } catch (e: Exception) {
                error = e.message
            }
            waiting = false
        }
    }

    @Composable
    fun LoadingScreen(isLoading: Boolean) {
        if (!isLoading) return
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.8f))
        ) {
            Text(
                text = stringResource(id = R.string.Loading),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun ErrorMessage(error: String?, onClick: () -> Unit) {
        if (error == null) return
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red.copy(alpha = 0.5f))
        ) {
            Text(text = error, modifier = Modifier.align(Alignment.Center))
            Button(
                onClick = onClick, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp)
            ) {
                Text(text = stringResource(id = R.string.Dismiss))
            }
        }
    }
}