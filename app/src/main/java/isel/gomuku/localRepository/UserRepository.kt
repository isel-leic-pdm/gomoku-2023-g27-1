package isel.gomuku.localRepository

interface UserRepository {
    fun getUser() : LoggedUser
    fun setUser(loggedUser: LoggedUser)
    fun deleteUser()
}
class UserDatabase : UserRepository {
    var loggedUser : LoggedUser?= LoggedUser(1,"Test",null)
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

data class LoggedUser(val id : Int, val nome: String, val token: String?)
