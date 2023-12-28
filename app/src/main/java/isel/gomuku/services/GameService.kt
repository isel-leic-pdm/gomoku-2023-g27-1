package isel.gomuku.services

import isel.gomuku.services.http.game.GameStart
import isel.gomuku.services.http.game.GameStateReturn
import isel.gomuku.services.http.game.httpModel.GameStatus

interface GameService {
    suspend fun play(line: Int, column: Int, auth: String):GameStateReturn?
    suspend fun startGame(gridSize: Int, variants: String, openingRules: String, auth: String): GameStatus
    suspend fun quitGame(auth: String)
    suspend fun getGameState(auth: String): GameStateReturn?
}