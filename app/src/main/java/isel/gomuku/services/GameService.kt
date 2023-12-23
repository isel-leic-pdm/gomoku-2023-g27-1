package isel.gomuku.services

interface GameService {
    suspend fun play(line: Int, column: Int, auth: String)
    suspend fun startGame(gridSize: Int, variants: String, openingRules: String, auth: String): Int
    suspend fun quitGame(auth: String)
    suspend fun getGameState(auth: String)
}