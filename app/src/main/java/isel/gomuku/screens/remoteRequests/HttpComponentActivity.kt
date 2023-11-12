package isel.gomuku.screens.remoteRequests

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

abstract class HttpComponentActivity<T> : ComponentActivity() where T : HttpViewModel {
    abstract val viewModel: T
    fun safeSetContent(screen: @Composable () -> Unit) {
        setContent {
            screen()
            viewModel.LoadingScreen(viewModel.waiting)
            viewModel.ErrorMessage(viewModel.error){viewModel.error = null}
        }
    }
}