package isel.gomuku.services.http.statistics

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.screens.component.LoadingScreen
import isel.gomuku.services.StatsService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.Problem
import isel.gomuku.services.http.RemoteSourceException
import isel.gomuku.services.http.statistics.model.LeaderBoard
import isel.gomuku.services.http.statistics.model.PlayerStats
import isel.gomuku.services.http.statistics.model.RankingModel
import isel.gomuku.services.http.statistics.model.Rankings
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class StatsServiceHttp(private val requestBuilder: HttpRequest, private val gson: Gson, private val baseApiUrl:String): StatsService {

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

    private suspend fun remoteRequest(request: Request, action:((Response) -> Unit)? = null){
        try {

            requestBuilder.doRequest(request){
                if (action != null) {
                    action(it)
                }
            }
        }catch (jsonException: JsonSyntaxException){
            throw IllegalStateException("Fatal error:" + jsonException.message)
        }catch (remote: RemoteSourceException){
            val prob = gson.fromJson(remote.body?.string(), Problem::class.java)
            throw Exception("Player not found")
        }
    }

    override suspend fun getRankings(page: Int, nickname: String?): LeaderBoard {
        val newUrl = rankingsUrl()
            .addPathSegment("android")
            .addPathSegment("$page")
            .addQueryParameter("nickname", nickname)
        val request = requestBuilder.get(newUrl, mapOf("accept" to "application/json"))
        var dto: LeaderBoard? = null
         remoteRequest(request){
            dto = gson.fromJson(it.body?.string(), LeaderBoard::class.java)
        }
        return dto!!
    }


    override suspend fun getGlobalStats(): GlobalStatistics {
        val request = requestBuilder.get(globalStatsUrl(), mapOf("accept" to "application/json"))
        var dto: GlobalStatisticsDto? = null
        remoteRequest(request) {
            dto = gson.fromJson(it.body?.string(), GlobalStatisticsDto::class.java)
        }
        return dto!!.toGlobalStatistics()
    }

    override suspend fun getPlayerStats(id:Int): PlayerStats {
        val newUrl = playerStatsUrl().addPathSegment("$id")
        val request = requestBuilder.get(newUrl,  mapOf("accept" to "application/json"))
        var dto: PlayerStatsDto? = null
        remoteRequest(request){
            dto = gson.fromJson(it.body?.string(), PlayerStatsDto::class.java)
        }
        return dto!!.toPlayerStats()
    }


    private data class GlobalStatisticsDto(val gameStats : GlobalStatistics) {
        fun toGlobalStatistics() = gameStats
    }
    private data class PlayerStatsDto (val userInfo: PlayerStats) {
        fun toPlayerStats () = userInfo
    }
}

