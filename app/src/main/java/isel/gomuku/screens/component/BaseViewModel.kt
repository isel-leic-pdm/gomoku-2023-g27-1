package isel.gomuku.screens.component

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Adds loading for to wait for a asynchronous request if needed and error displaying
 * */
abstract class BaseViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    protected fun safeCall(function: suspend () -> Unit) {

        //TODO:Check concurrency
        isLoading = true
        viewModelScope.launch() {
            val async = launch(Dispatchers.IO) {
                try {
                    Log.d("Test", "async start ${Thread.currentThread().name + Thread.currentThread().id}")
                    function()
                } catch (e: Exception) {
                    Log.d("Test", "error", e)
                    withContext(Dispatchers.Main){
                        error = e.message.toString()//TODO:Proper error presentation
                    }
                }
            }
            async.join()
            isLoading = false
        }

    }
}

