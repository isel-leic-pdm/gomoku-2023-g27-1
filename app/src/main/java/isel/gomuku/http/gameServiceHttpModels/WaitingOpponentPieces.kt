package isel.gomuku.http.gameServiceHttpModels

class WaitingOpponentPieces(val lobbyId: Int,val gridSize:Int,val moves:List<Move>,val opponentId:Int)

class WaitingOpponentPiecesOutput(val gameOpened:WaitingOpponentPieces,val opponent:UserInfo?)