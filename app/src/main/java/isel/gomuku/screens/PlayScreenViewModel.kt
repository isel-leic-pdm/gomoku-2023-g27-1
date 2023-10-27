package isel.gomuku.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import isel.gomuku.gameLogic.Board
import isel.gomuku.gameLogic.BoardRun
import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
import isel.gomuku.services.GomokuService

class PlayScreenViewModel() : ViewModel() {

     val service by lazy {
        GomokuService()
    }
    val boardSize = service.gridSize //TODO(SORT HOW TO GET VALUE)
    var board: Board? by mutableStateOf(null)
    var waiting by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    private fun request(httpRequest: () -> Unit) {
        waiting = true
        try {
            //adicionar pedido http
            httpRequest()
        } catch (e: Exception) {
            error = e.message
        }
        waiting = false
    }

    fun play(pos: Position) {
        //request { board = board?.play(pos, board.lastPlayer.turn()) }
    }

    fun quit() {
        request { }
    }


}