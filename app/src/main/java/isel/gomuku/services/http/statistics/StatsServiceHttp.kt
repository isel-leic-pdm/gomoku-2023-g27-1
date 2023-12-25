package isel.gomuku.services.http.statistics

import android.util.Log
import com.google.gson.Gson
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.statistics.model.RankingModel
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
    suspend fun getRankings(nextPage : String): RankingModel {
        val newUrl = rankingsUrl().addPathSegment(nextPage)
        val request = httpRequests.get(newUrl, hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request) {
            val dto = gson.fromJson(it.body?.string(), RankingModel::class.java)
            return@doRequest dto
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

