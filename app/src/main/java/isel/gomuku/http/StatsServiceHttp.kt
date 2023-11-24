package isel.gomuku.http

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import isel.gomuku.services.FetchException
import isel.gomuku.services.StatsService
import isel.gomuku.services.dto.BestPlayerRanking
import isel.gomuku.services.dto.DefeatsRanking
import isel.gomuku.services.dto.GamesRanking
import isel.gomuku.services.dto.GlobalStatistics
import isel.gomuku.services.dto.Rankings
import isel.gomuku.services.dto.TimePlayedRanking
import isel.gomuku.services.dto.VictoriesRanking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StatsServiceHttp(val client: OkHttpClient, val gson: Gson, val baseApiUrl:String) : StatsService {

    val globalStatsUrl = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("statistics")
    }

    val rankingsUrl  = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("statistics")
            .addPathSegment("ranking")
    }
    val httpRequests = HttpRequest (client)

    private val listDeserializationType = object : TypeToken<List<NasaImageDto>>() {}.type
    override suspend fun getRankings(): Rankings {
        return suspendCoroutine {
            client.newCall(rankingRequest).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(FetchException("Failed to fetch ranking", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchException("Failed to fetch ranking: ${response.code}"))
                    else {
                        val dto = gson.fromJson(body.string(), RankingsDto::class.java)
                        it.resumeWith(Result.success(dto.toRankings()))
                    }
                }
            })
        }
    }



    override suspend fun getGlobalStats(): GlobalStatistics {
        return suspendCoroutine {
            client.newCall(globalStatsRequest).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(FetchException("Failed to fetch statistics", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchException("Failed to fetch global statistics: ${response.code}"))
                    else {
                        val dto = gson.fromJson(body.string(), GlobalStatistics::class.java)
                        it.resumeWith(Result.success(dto))
                    }
                }
            })
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

