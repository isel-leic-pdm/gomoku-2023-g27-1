package isel.gomuku.services.http.user

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import isel.gomuku.repository.user.UserRepository
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.UserService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.RemoteSourceException
import isel.gomuku.services.http.user.model.LoginBody
import isel.gomuku.services.http.user.model.UserAuthorization
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class Problem(val status:String,val title:String, val type : String,val detail:String)
class UserServiceHttp(
    private val requestBuilder: HttpRequest,
    private val gson: Gson,
    private val baseApiUrl: String,
    private val userRepository: UserRepository
) : UserService {

    private val userURI =
        baseApiUrl.toHttpUrl().newBuilder().addPathSegment("user")

    private val loginURI = userURI.addPathSegment("login")
    override fun getUser(): LoggedUser? {
        return userRepository.getUser()
    }
    private suspend fun remoteRequest(request: Request,action:(Response) -> Unit){
        try {
            requestBuilder.doRequest(request){
                action(it)
            }
        }catch (jsonException:JsonSyntaxException){
            throw IllegalStateException("Fatal error:" + jsonException.message)
        }catch (remote:RemoteSourceException){
            val prob = gson.fromJson(remote.body?.string(),Problem::class.java)
            throw Exception(if (prob.detail != null)prob.title +":" + prob.detail else prob.title)
        }
    }
    override suspend fun login(userName: String, password: String): LoggedUser {
        val login = LoginBody(userName, password)
        val body = gson.toJson(login).toRequestBody("application/json".toMediaType())
        val request = requestBuilder.post(
            loginURI,
            body
        )
        var user : LoggedUser? = null
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),UserAuthorization::class.java)
            Log.d("Test",dto.toString())
            user = LoggedUser(dto.id,dto.nickname,dto.tokenInfo.tokenValue)
        }
        userRepository.setUser(user!!)

        return user!!
    }

    override fun register(userName: String, email: String, password: String): LoggedUser {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    companion object {
        const val API_NICKNAME_KEY = "nickname"
        const val API_PASSWORD_KEY = "password"
        const val API_EMAIL_KEY = "email"
    }

}