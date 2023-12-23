package isel.gomuku.repository.user

import isel.gomuku.repository.user.model.LoggedUser

class UserMem : UserRepository {
    private var loggedUser : LoggedUser? = null
    override fun getUser(): LoggedUser? {
        return loggedUser
    }

    override fun setUser(loggedUser: LoggedUser) {
        this.loggedUser = loggedUser
    }

    override fun deleteUser() {
        loggedUser = null
    }
}
