package isel.gomuku.services

import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.http.user.model.UserProfile

interface UserService {
    suspend fun getUser(): LoggedUser?
    suspend fun login(userName: String, password: String): LoggedUser
    suspend fun register(userName: String, email: String, password: String): LoggedUser
    suspend fun userProfile ():UserProfile
    suspend fun logout()
}
