package isel.gomuku.services.http.game.httpModel

class GameEnded(
    val winner: Int?,
    val gridSize: Int,
    val moves: List<Move>,
    val playerPiece: GoPiece?,
    val opponentId: Int
)

class  GameEndedOutput(val gameEnded: GameEnded,val opponent:UserInfo?)