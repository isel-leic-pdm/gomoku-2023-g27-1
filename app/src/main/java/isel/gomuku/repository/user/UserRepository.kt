package isel.gomuku.repository.user

import isel.gomuku.repository.user.model.LoggedUser

interface UserRepository {
    fun getUser() : LoggedUser?
    fun setUser(loggedUser: LoggedUser)
    fun deleteUser()
}