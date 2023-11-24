package isel.gomuku

import android.app.Application
import com.google.gson.Gson

import isel.gomuku.http.GameServiceHttp
import isel.gomuku.http.StatsServiceHttp
import okhttp3.OkHttpClient

class GomokuApplication : Application() {

    val gson = Gson()

    val client =
        OkHttpClient.Builder()
            .build()

    val gameService = GameServiceHttp(client, gson)
    val statsService = StatsServiceHttp(client, gson)
}