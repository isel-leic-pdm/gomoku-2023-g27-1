package isel.gomuku.services.http.requestBody

data class RegisterBody(val nickname: String, val email:String, val password: String): GomokuRequestBody()
