package isel.gomuku.services.http.user

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import isel.gomuku.repository.user.UserRepository
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.UserService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.Problem
import isel.gomuku.services.http.RemoteSourceException
import isel.gomuku.services.http.requestBody.LoginBody
import isel.gomuku.services.http.requestBody.RegisterBody
import isel.gomuku.services.http.requestBody.GomokuRequestBody
import isel.gomuku.services.http.user.model.UserAuthorization
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response


class UserServiceHttp(
    private val requestBuilder: HttpRequest,
    private val gson: Gson,
    private val baseApiUrl: String,
    private val userRepository: UserRepository
) : UserService {

    private val userURI ={
        baseApiUrl.toHttpUrl().newBuilder().addPathSegment("user")
    }

    private val loginURI = { userURI().addPathSegment("login") }
    private val logoutURI = { userURI().addPathSegment("logout") }
    private val registerURI = { userURI().addPathSegment("register")}

    private suspend fun remoteRequest(request: Request,action:((Response) -> Unit)? = null){
        try {
            requestBuilder.doRequest(request){
                if (action != null) {
                    action(it)
                }
            }
        }catch (jsonException:JsonSyntaxException){
            throw IllegalStateException("Fatal error:" + jsonException.message)
        }catch (remote:RemoteSourceException){
            val prob = gson.fromJson(remote.body?.string(), Problem::class.java)
            throw Exception(if (prob.detail != null)prob.title +":" + prob.detail else prob.title)
        }
    }
    private suspend fun authenticateUser(login: GomokuRequestBody,path:HttpUrl.Builder): LoggedUser {
        val body = gson.toJson(login).toRequestBody("application/json".toMediaType())
        val request = requestBuilder.post(
            path,
            body
        )
        var user: LoggedUser? = null
        remoteRequest(request) {
            val dto = gson.fromJson(it.body?.string(), UserAuthorization::class.java)
            Log.d("Test", dto.toString())
            user = LoggedUser(dto.id, dto.nickname, dto.tokenInfo.tokenValue)
        }
        userRepository.setUser(user!!)
        return user!!
    }

    override suspend fun login(userName: String, password: String): LoggedUser {
        val login = LoginBody(userName, password)
        return authenticateUser(login,loginURI())
    }
    override suspend fun register(userName: String, email: String, password: String): LoggedUser {
        val register = RegisterBody(userName,email, password)
        return authenticateUser(register,registerURI())
    }

    override suspend fun getUser(): LoggedUser? {
        return userRepository.getUser()
    }


    override suspend fun logout() {
        val token = userRepository.getUser()?.token
            ?: throw IllegalStateException("User as not logged in yet")
        val request = requestBuilder.post(
            logoutURI(),
            headers = mapOf("Authorization" to "Bearer $token")
        )
        remoteRequest(request)
    }

    companion object {
        const val API_NICKNAME_KEY = "nickname"
        const val API_PASSWORD_KEY = "password"
        const val API_EMAIL_KEY = "email"
    }

}
