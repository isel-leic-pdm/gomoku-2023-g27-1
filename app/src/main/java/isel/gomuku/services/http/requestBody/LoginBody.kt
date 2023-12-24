package isel.gomuku.services.http.requestBody

data class LoginBody(val nickname: String, val password: String) : GomokuRequestBody()
