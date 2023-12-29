package isel.gomuku.services.http.user.model

class UserProfile (
             val id: Int,
             val nickname: String,
             val email: String,
             val points: Int,
             val victories: Int,
             val defeats: Int,
             val draws: Int,
             val gamesPlayed: Int,
             val timePlayed: String
)