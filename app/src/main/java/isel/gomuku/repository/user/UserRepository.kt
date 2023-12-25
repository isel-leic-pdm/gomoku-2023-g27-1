package isel.gomuku.repository.user

import isel.gomuku.repository.user.model.LoggedUser

interface UserRepository {
    suspend fun getUser() : LoggedUser?
    suspend fun setUser(loggedUser: LoggedUser)
    suspend fun deleteUser()
}