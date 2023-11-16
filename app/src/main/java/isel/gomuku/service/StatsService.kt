package isel.gomuku.service

import isel.gomuku.service.dto.BestPlayerRanking
import isel.gomuku.service.dto.DefeatsRanking
import isel.gomuku.service.dto.GamesRanking
import isel.gomuku.service.dto.GlobalStatistics
import isel.gomuku.service.dto.Rankings
import isel.gomuku.service.dto.TimePlayedRanking
import isel.gomuku.service.dto.VictoriesRanking

class StatsServiceLocal:StatsService {


    private val  rankings = Rankings (
        mutableListOf(
            BestPlayerRanking("marsul", 3500),
                      BestPlayerRanking("simao", 3000) ),
        mutableListOf(
            VictoriesRanking("marsul", 5),
            VictoriesRanking("simao", 3) ),
        mutableListOf(
            GamesRanking("marsul", 8),
            GamesRanking("simao", 7) ),
        mutableListOf(
            TimePlayedRanking("marsul", "50:10:6"),
            TimePlayedRanking("simao", "50:10:6") ),
        mutableListOf(
            DefeatsRanking("marsul", 6),
            DefeatsRanking("simao", 3) ),
    )

    private val globalStatistics = GlobalStatistics("100:10:32",50,50)

    override suspend fun getRankings(): Rankings {
        return rankings
    }

    override suspend fun getGlobalStats(): GlobalStatistics {
        return globalStatistics
    }
}
interface StatsService {
    suspend fun getRankings (): Rankings
    suspend fun getGlobalStats(): GlobalStatistics
}

