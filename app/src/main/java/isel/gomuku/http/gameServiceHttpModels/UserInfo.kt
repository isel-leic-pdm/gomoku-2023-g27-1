package isel.gomuku.http.gameServiceHttpModels

class UserInfo(
    val nickname: String,
    val points: Int,
    val victories: Int,
    val defeats: Int,
    val draws: Int,
    val gamesPlayed: Int,
    val timePlayed: String)