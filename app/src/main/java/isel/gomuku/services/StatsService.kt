package isel.gomuku.services

import isel.gomuku.services.http.statistics.model.GlobalStatistics
import isel.gomuku.services.http.statistics.model.Rankings

interface StatsService {
    suspend fun getRankings(): Rankings
    suspend fun getGlobalStats(): GlobalStatistics
}