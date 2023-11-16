package isel.gomuku.screens.gameScreeens.ranking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.gomuku.service.StatsService
import isel.gomuku.service.dto.GlobalStatistics
import isel.gomuku.service.dto.Rankings
import kotlinx.coroutines.launch

class RankingViewModel:ViewModel() {
    var rankings : Rankings? by mutableStateOf(null)
        private set
    var globalStatistics : GlobalStatistics? by mutableStateOf(null)
        private set

    var currentState : RankingMenuState by mutableStateOf(RankingMenuState.MENU)

    fun changeStatsToShow (state: RankingMenuState) {
        currentState = state
    }
    fun getRankings (service : StatsService) {
        viewModelScope.launch {
            rankings = service.getRankings()
        }
    }
    fun getGlobalStats (service : StatsService) {
        viewModelScope.launch {
            globalStatistics = service.getGlobalStats()
        }
    }
}

