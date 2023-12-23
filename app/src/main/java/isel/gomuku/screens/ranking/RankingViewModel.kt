package isel.gomuku.screens.ranking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.services.http.statistics.StatsServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.services.http.statistics.model.GlobalStatistics
import isel.gomuku.services.http.statistics.model.Rankings

class RankingViewModel:BaseViewModel() {
    var rankings : Rankings? by mutableStateOf(null)
        private set
    var globalStatistics: GlobalStatistics? by mutableStateOf(null)
        private set

    var currentState : RankingMenuState by mutableStateOf(RankingMenuState.MENU)

    fun changeStatsToShow (state: RankingMenuState) {
        currentState = state
    }
    fun getRankings (service : StatsServiceHttp) {
        safeCall {
               rankings = service.getRankings()
        }

    }
    fun getGlobalStats (service : StatsServiceHttp) {
        safeCall {
            globalStatistics = service.getGlobalStats()
        }
    }
}

