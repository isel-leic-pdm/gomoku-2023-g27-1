package isel.gomuku

import android.app.Application
import com.google.gson.Gson

import isel.gomuku.httpServices.GameServiceHttp
import isel.gomuku.httpServices.StatsServiceHttp
import isel.gomuku.localRepository.UserDatabase
import isel.gomuku.localRepository.UserRepository
import isel.gomuku.helpers.DependencyContainer
import okhttp3.OkHttpClient

class GomokuApplication : Application(), DependencyContainer {

    private val gson = Gson()

    private val client =
        OkHttpClient.Builder()
            .build()

    override val userStorage: UserRepository by lazy {
        UserDatabase()
    }
    override val gameService by lazy {
        GameServiceHttp(client, gson, REMOTE_GAME_API_BASE_URL)
    }
    override val statsService by lazy {
        StatsServiceHttp(client, gson, REMOTE_GAME_API_BASE_URL)
    }

    companion object {
        const val REMOTE_GAME_API_BASE_URL = "http://10.0.2.2:8080/api"
    }
}