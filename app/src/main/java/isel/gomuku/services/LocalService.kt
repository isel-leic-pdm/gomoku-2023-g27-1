package isel.gomuku.services

import isel.gomuku.gameLogic.BoardRun
import isel.gomuku.gameLogic.Player
import isel.gomuku.screens.gameScreeens.RunningGame


interface Services{
}
class LocalService() {
    private val GRID_SIZE = 15
     fun play(){
        TODO()
    }

    fun quit(){
        TODO()
    }

    fun startGame(): RunningGame {
        val startBoard = BoardRun.startBoard(GRID_SIZE)
        return RunningGame(BoardRun(startBoard,Player.WHITE),Player.BLACK)
    }
}