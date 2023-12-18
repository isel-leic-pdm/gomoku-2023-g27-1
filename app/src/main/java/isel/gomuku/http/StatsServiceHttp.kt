package isel.gomuku.http

import com.google.gson.Gson
import isel.gomuku.services.StatsService
import isel.gomuku.gameLogic.model.statistics.BestPlayerRanking
import isel.gomuku.gameLogic.model.statistics.DefeatsRanking
import isel.gomuku.gameLogic.model.statistics.GamesRanking
import isel.gomuku.gameLogic.model.statistics.GlobalStatistics
import isel.gomuku.gameLogic.model.statistics.Rankings
import isel.gomuku.gameLogic.model.statistics.TimePlayedRanking
import isel.gomuku.gameLogic.model.statistics.VictoriesRanking
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient

class StatsServiceHttp(client: OkHttpClient, private val gson: Gson, private val baseApiUrl:String) : StatsService {

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

    override suspend fun getRankings(): Rankings {
        val request = httpRequests.get(rankingsUrl(), hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request) {
            return@doRequest gson.fromJson(it.body?.string(), Rankings::class.java)
        }
    }


    override suspend fun getGlobalStats(): GlobalStatistics {
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

