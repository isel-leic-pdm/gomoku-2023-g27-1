package isel.gomuku.screens.ranking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.http.StatsServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.services.dto.GlobalStatistics
import isel.gomuku.services.dto.Rankings

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

