package isel.gomuku.screens.ranking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.gomuku.helpers.Idle
import isel.gomuku.helpers.LoadState
import isel.gomuku.helpers.Loaded
import isel.gomuku.helpers.Loading
import isel.gomuku.http.StatsServiceHttp
import isel.gomuku.services.StatsService
import isel.gomuku.services.dto.GlobalStatistics
import isel.gomuku.services.dto.Rankings
import kotlinx.coroutines.launch

class RankingViewModel( ):ViewModel() {
    var rankings by mutableStateOf<LoadState<Rankings>>(Idle)
        private set
    var globalStatistics  by mutableStateOf<LoadState<GlobalStatistics>>(Idle)
        private set

    var currentState : RankingMenuState by mutableStateOf(RankingMenuState.MENU)

    fun changeStatsToShow (state: RankingMenuState) {
        currentState = state
    }
    fun getRankings (service : StatsServiceHttp) {
        viewModelScope.launch {
            rankings = Loading
            rankings = Loaded(runCatching { service.getRankings() })
        }
    }
    fun getGlobalStats (service : StatsServiceHttp) {
        viewModelScope.launch {
            globalStatistics = Loading
            globalStatistics = Loaded(runCatching { service.getGlobalStats() })
        }
    }
}

