package isel.gomuku.services.http.user

import com.google.gson.Gson
import isel.gomuku.repository.user.UserRepository
import isel.gomuku.services.UserService
import okhttp3.OkHttpClient

class UserServiceHttp(
    private val client: OkHttpClient,
    private val gson: Gson,
    private val baseApiUrl: String,
    private val userRepository: UserRepository
) : UserService {

}