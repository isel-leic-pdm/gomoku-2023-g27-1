package isel.gomuku

import android.app.Application
import com.google.gson.Gson

import isel.gomuku.http.GameServiceHttp
import isel.gomuku.http.StatsServiceHttp
import isel.gomuku.localRepository.UserDatabase
import isel.gomuku.localRepository.UserRepository
import isel.gomuku.services.DependencyContainer
import okhttp3.OkHttpClient

class GomokuApplication : Application(), DependencyContainer {

    val gson = Gson()

    val client =
        OkHttpClient.Builder()
            .build()

    override val userStorage: UserRepository by lazy {
        UserDatabase()
    }
    override val gameService by lazy {
        GameServiceHttp(client, gson)
    }
    override val statsService by lazy {
        StatsServiceHttp(client, gson)
    }

    companion object {
        const val REMOTE_GAME_API_BASE_URL = "http://localhost:8080/api"
    }
}