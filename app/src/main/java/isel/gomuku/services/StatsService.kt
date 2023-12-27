package isel.gomuku.services

import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.services.http.statistics.model.PlayerStats
import isel.gomuku.services.http.statistics.model.RankingModel
import isel.gomuku.services.http.statistics.model.Rankings

interface StatsService {
    suspend fun getRankings(page:String): RankingModel
    suspend fun getGlobalStats(): GlobalStatistics
    suspend fun getPlayerStats(id:Int):PlayerStats
}