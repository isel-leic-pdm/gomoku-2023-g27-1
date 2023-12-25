package isel.gomuku.repository.user

import isel.gomuku.repository.user.model.LoggedUser

class UserMem : UserRepository {
    private var loggedUser : LoggedUser? = null
    override suspend fun getUser(): LoggedUser? {
        return loggedUser
    }

    override suspend fun setUser(loggedUser: LoggedUser) {
        this.loggedUser = loggedUser
    }

    override suspend fun deleteUser() {
        loggedUser = null
    }
}
