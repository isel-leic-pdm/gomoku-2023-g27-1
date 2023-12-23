package isel.gomuku.services.http.game.httpModel

class GameRunning(
    val lobbyId: Int,
    val gridSize: Int,
    val playerPiece: GoPiece,
    val isPlayerTurn: Boolean,
    val moves: List<Move>,
    val opponentId: Int
)
class GameRunningOutput(val gameOpened: GameRunning, val opponent: UserInfo?)