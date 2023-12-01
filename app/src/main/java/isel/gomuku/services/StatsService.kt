package isel.gomuku.services

import isel.gomuku.gameLogic.model.statistics.BestPlayerRanking
import isel.gomuku.gameLogic.model.statistics.DefeatsRanking
import isel.gomuku.gameLogic.model.statistics.GamesRanking
import isel.gomuku.gameLogic.model.statistics.GlobalStatistics
import isel.gomuku.gameLogic.model.statistics.Rankings
import isel.gomuku.gameLogic.model.statistics.TimePlayedRanking
import isel.gomuku.gameLogic.model.statistics.VictoriesRanking


class StatsServiceLocal: StatsService {


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

class FetchException(message: String, cause: Throwable? = null)
    : Exception(message, cause)