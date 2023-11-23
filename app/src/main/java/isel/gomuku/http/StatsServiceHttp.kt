package isel.gomuku.http

import com.google.gson.Gson
import isel.gomuku.services.StatsService
import isel.gomuku.services.dto.GlobalStatistics
import isel.gomuku.services.dto.Rankings
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StatsServiceHttp (client: OkHttpClient, gson: Gson):StatsService {


    private val request: Request by lazy {
        Request.Builder()
            .url("http://localhost:8080/statistics/ranking")
            .addHeader("accept", "application/json")
            .build()
    }
    override suspend fun getRankings(): Rankings {
        TODO()
        }

    override suspend fun getGlobalStats(): GlobalStatistics {
        TODO()
    }


}