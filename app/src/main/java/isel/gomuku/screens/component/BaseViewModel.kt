package isel.gomuku.screens.component

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


/**
 * Adds loading for to wait for a asynchronous request if needed and error displaying
 * */
abstract class BaseViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    protected fun safeCall(function: suspend () -> Unit) {
        isLoading = true

        viewModelScope.launch {
            try {
                function()
            } catch (e: Exception) {
                Log.d("Test","error",e)
                error = e.message.toString()//TODO:Proper error presentation
            }
            isLoading = false
        }
    }
}