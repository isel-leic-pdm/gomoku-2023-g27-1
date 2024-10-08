package isel.gomuku.services.http.game.httpModel

import isel.gomuku.services.local.gameLogic.Player

sealed class GameStatus
/**
 * Does not represent game state only contains game Data.
 * */
class GameDetails(val lobbyId: Int, val gridSize:Int, val openingRule: String, val variant: String) : GameStatus()
/**
 * Ongoing game.
 *
 * @param lobbyId ID of the game.
 * @param playerPiece Piece owned by the player that made the request
 * @param isPlayerTurn True when its turn of the player that made the request.
 * */
class GameRunning(val lobbyId: Int,val gridSize:Int, val playerPiece: Player, val isPlayerTurn: Boolean,val moves:List<Move>,val opponentId:Int) : GameStatus(){
    var opponent : UserInfo? = null
}
/**
 * The Board is in an opening stage where the current player must place the pieces obeying the opening rule selected.
 *
 *  @param lobbyId ID of the game.
 *  @param desiredPieces String representing the number of pieces that must be placed to obey the opening rule, if the
 *  number as more than a single digit, then the user as multiple options represented by each digit.
 * */
class GameOpened(val lobbyId: Int,val gridSize:Int,val desiredPieces: String,val moves:List<Move>,val opponentId:Int) : GameStatus()
/**
 * Player is waiting for an opponent in the lobby.
 * */
class AwaitingOpponent(val lobbyId: Int,val gridSize:Int) : GameStatus()
/**
 * The Board is in an opening stage where the current player must wait for the opponent to place the  pieces
 * obeying the opening selected.
 * */
class WaitingOpponentPieces(val lobbyId: Int,val gridSize:Int,val moves:List<Move>,val opponentId:Int) : GameStatus(){
    var opponent : UserInfo? = null
}

/**
 * Game as finished with a winner or a draw (winner equals null).
 * */
class GameEnded(val winner: Int?,val gridSize:Int,val moves: List<Move>, val playerPiece: GoPiece?,val opponentId:Int) : GameStatus(){
    var opponent : UserInfo? = null
}

class PlayMade(val lobbyId: Int) : GameStatus()
class LobbyClosed(val lobbyId: Int) : GameStatus()