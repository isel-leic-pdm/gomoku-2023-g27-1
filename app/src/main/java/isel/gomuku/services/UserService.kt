package isel.gomuku.services

import isel.gomuku.repository.user.model.LoggedUser

interface UserService {
    fun getUser(): LoggedUser?
    suspend fun login(userName: String, password: String): LoggedUser
    fun register(userName: String, email: String, password: String): LoggedUser
    suspend fun logout()
}