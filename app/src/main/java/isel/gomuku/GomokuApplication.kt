package isel.gomuku

import android.app.Application
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import isel.gomuku.repository.user.UserDataStore
import isel.gomuku.repository.user.UserMem

import isel.gomuku.services.http.game.GameServiceHttp
import isel.gomuku.services.http.statistics.StatsServiceHttp
import isel.gomuku.repository.user.UserRepository
import isel.gomuku.services.UserService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.user.UserServiceHttp
import isel.gomuku.utils.DependencyContainer
import okhttp3.OkHttpClient

class GomokuApplication : Application(), DependencyContainer {

    private val gson = Gson()

    private val client =
        OkHttpClient.Builder()
            .build()

    private val dataStore by preferencesDataStore(
        name = UserPreferencesDataStoreName
    )
    private val userRepo : UserRepository by lazy {
        /*UserMem()*/ /*Or*/ UserDataStore(dataStore)
    }
    private val requestBuilder = HttpRequest(client)

    override val userService: UserService by lazy {
        UserServiceHttp(requestBuilder,gson, REMOTE_GAME_API_BASE_URL,userRepo)
    }
    override val gameService by lazy {
        GameServiceHttp(requestBuilder, gson, REMOTE_GAME_API_BASE_URL)
    }
    override val statsService by lazy {
        StatsServiceHttp(HttpRequest(client), gson, REMOTE_GAME_API_BASE_URL)
    }

    companion object {
        const val REMOTE_GAME_API_BASE_URL = "http://10.0.2.2:8080/api"
        const val UserPreferencesDataStoreName = "UserInfo"
    }
}