package isel.gomuku.localRepository

import android.provider.ContactsContract.CommonDataKinds.Email

interface UserRepository {
    fun getUser() : LoggedUser
    fun setUser(loggedUser: LoggedUser)
    fun deleteUser()
}
class UserDatabase : UserRepository {
    var loggedUser : LoggedUser? = null
    override fun getUser(): LoggedUser {
        return loggedUser ?: throw IllegalStateException("User not logged")
    }

    override fun setUser(loggedUser: LoggedUser) {
        this.loggedUser = loggedUser
    }

    override fun deleteUser() {
        loggedUser = null
    }


}

data class LoggedUser(val id : Int, val nome: String,val email: String, val token: String)
