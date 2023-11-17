package isel.gomuku.screens.component

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

abstract class BaseComponentActivity<T> : ComponentActivity() where T : BaseViewModel {

    abstract val viewModel: T

    // val dependencyContainer by lazy { (this.application as NasaApplication) }
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