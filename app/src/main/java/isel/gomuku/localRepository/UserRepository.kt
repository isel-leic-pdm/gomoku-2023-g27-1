/*
package isel.gomuku.localRepository

interface UserRepository {
    fun getToken(): String
    fun getUser() : User
    fun setToken(token: String)
}
class UserDatabase : UserRepository {
    var user = User(1,"Test",null)
    override fun getToken(): String = "User token"
    override fun getUser(): User{
        return user
    }
    override fun setToken(token: String) {
        val u = user
        user = u.copy(token = token)
    }

}

data class User(val id : Int,val nome: String, val token: String?) {

}
*/
