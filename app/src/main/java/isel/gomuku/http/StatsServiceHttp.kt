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
            val dto = gson.fromJson(it.body?.string(), RankingsDto::class.java)
            return@doRequest dto.toRankings()
        }
    }


    override suspend fun getGlobalStats(): GlobalStatistics {
        val request = httpRequests.get(globalStatsUrl(), hashMapOf("accept" to "application/json"))
        return httpRequests.doRequest(request) {
            return@doRequest gson.fromJson(it.body?.string(), GlobalStatistics::class.java)
        }
    }

    private data class RankingsDto(
        val bestPlayers: List<BestPlayerRanking>,
        val victories: List<VictoriesRanking>,
        val mostGames: List<GamesRanking>,
        val mostTime: List<TimePlayedRanking>,
        val playerDefeats: List<DefeatsRanking>
    ) {
        fun toRankings() = Rankings(bestPlayers, victories, mostGames, mostTime, playerDefeats)
    }
}

