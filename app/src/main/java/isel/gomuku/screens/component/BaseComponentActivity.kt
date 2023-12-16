package isel.gomuku.screens.component

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import isel.gomuku.GomokuApplication

abstract class BaseComponentActivity<T> : ComponentActivity() where T : BaseViewModel {

    abstract val viewModel: T

    val dependencyContainer by lazy { (this.application as GomokuApplication) }
    fun safeSetContent(screen: @Composable () -> Unit) {
        setContent {
            screen()
            if (viewModel.isLoading)
                LoadingScreen()
            val error = viewModel.error
            if (error != null) {
                ErrorMessage(error) {
                    viewModel.error = null
                }
            }
        }
    }
}