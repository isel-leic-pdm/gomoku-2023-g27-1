package isel.gomuku.services.http.game.httpModel

class UserInfo(
    val nickname: String,
    val points: Int,
    val victories: Int,
    val defeats: Int,
    val draws: Int,
    val gamesPlayed: Int,
    val timePlayed: String)