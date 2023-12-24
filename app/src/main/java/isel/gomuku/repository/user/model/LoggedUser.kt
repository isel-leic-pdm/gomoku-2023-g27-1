package isel.gomuku.repository.user.model

import isel.gomuku.services.http.user.model.Token

data class LoggedUser(val id : Int, val nome: String,val token:String)
