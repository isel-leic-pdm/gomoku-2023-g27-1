package isel.gomuku.services.http.user.model

data class Token(val tokenValue: String,val tokenExpiration:String,val maxAge:Int)
