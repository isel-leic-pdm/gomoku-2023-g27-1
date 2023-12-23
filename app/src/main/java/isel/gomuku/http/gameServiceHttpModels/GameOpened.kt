package isel.gomuku.http.gameServiceHttpModels

class GameOpened(
    val lobbyId: Int,
    val gridSize: Int,
    val desiredPieces: String,
    val moves: List<Move>,
    val opponentId: Int
)

class GameOpenedOutput(val gameOpened:GameOpened,val opponent:UserInfo?)