package isel.gomuku.http.gameServiceHttpModels

class GameEnded(
    val winner: Int?,
    val gridSize: Int,
    val moves: List<Move>,
    val playerPiece: GoPiece?,
    val opponentId: Int
)