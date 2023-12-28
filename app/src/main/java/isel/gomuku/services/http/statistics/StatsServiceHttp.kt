package isel.gomuku.services.http.statistics

import android.util.Log
import com.google.gson.Gson
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.services.StatsService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.statistics.model.LeaderBoard
import isel.gomuku.services.http.statistics.model.PlayerStats
import isel.gomuku.services.http.statistics.model.RankingModel
import isel.gomuku.services.http.statistics.model.Rankings
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient

class StatsServiceHttp(client: OkHttpClient, private val gson: Gson, private val baseApiUrl:String): StatsService {

    val globalStatsUrl = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("statistics")
    }

    val rankingsUrl  = {
        globalStatsUrl()
            .addPathSegment("ranking")
    }
    val playerStatsUrl = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("user")
            .addPathSegment("id-info")
    }

    private val httpRequests = HttpRequest (client)

//    override suspend fun getRankings(nextPage : String): RankingModel {
//        val newUrl = rankingsUrl()
//            .addPathSegment(nextPage)
//        val request = httpRequests.get(newUrl, hashMapOf("accept" to "application/json"))
//        return httpRequests.doRequest(request) {
//            val dto = gson.fromJson(it.body?.string(), RankingModel::class.java)
//            return@doRequest dto
//        }
//    }

    override suspend fun getRankings(page: Int, nickname: String?): LeaderBoard {
        val newUrl = rankingsUrl()
            .addPathSegment("android")
            .addPathSegment("$page")
            .addQueryParameter("nickname", nickname)
        val request = httpRequests.get(newUrl, hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request) {
            return@doRequest gson.fromJson(it.body?.string(), LeaderBoard::class.java)
        }
    }


    override suspend fun getGlobalStats(): GlobalStatistics {
        val request = httpRequests.get(globalStatsUrl(), hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request) {
            val dto = gson.fromJson(it.body?.string(), GlobalStatisticsDto::class.java)
            return@doRequest dto.toGlobalStatistics()
        }
    }

    override suspend fun getPlayerStats(id:Int): PlayerStats {
        val newUrl = playerStatsUrl().addPathSegment("$id")
        val request = httpRequests.get(newUrl,  hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request){
            val dto = gson.fromJson(it.body?.string(), PlayerStatsDto::class.java)
            return@doRequest dto.toPlayerStats()
        }
    }


    private data class GlobalStatisticsDto(val gameStats : GlobalStatistics) {
        fun toGlobalStatistics() = gameStats
    }

    private data class PlayerStatsDto (val userInfo: PlayerStats) {
        fun toPlayerStats () = userInfo
    }
}

