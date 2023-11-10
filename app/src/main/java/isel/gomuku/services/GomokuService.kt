package isel.gomuku.services

import isel.gomuku.gameLogic.OpenGame


interface Services{
}
class UserServices : Services
class GomokuService() {
    suspend fun play(){
        TODO()
    }

    suspend fun quit(){
        TODO()
    }

    suspend fun startGame(): OpenGame {
        TODO()
    }
}