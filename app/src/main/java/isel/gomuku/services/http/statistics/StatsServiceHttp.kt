package isel.gomuku.services.http.statistics

import com.google.gson.Gson
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.statistics.model.GlobalStatistics
import isel.gomuku.services.http.statistics.model.Rankings
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient

class StatsServiceHttp(client: OkHttpClient, private val gson: Gson, private val baseApiUrl:String) {

    val globalStatsUrl = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("statistics")
    }

    val rankingsUrl  = {
        globalStatsUrl()
            .addPathSegment("ranking")
    }

    private val httpRequests = HttpRequest (client)
    suspend fun getRankings(): Rankings {
        val request = httpRequests.get(rankingsUrl(), hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request) {
            return@doRequest gson.fromJson(it.body?.string(), Rankings::class.java)
        }
    }


    suspend fun getGlobalStats(): GlobalStatistics {
        val request = httpRequests.get(globalStatsUrl(), hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request) {
            val dto = gson.fromJson(it.body?.string(), GlobalStatisticsDto::class.java)
            return@doRequest dto.toGlobalStatistics()
        }
    }

    private data class GlobalStatisticsDto(val gameStats : GlobalStatistics) {
        fun toGlobalStatistics() = gameStats
    }
}

