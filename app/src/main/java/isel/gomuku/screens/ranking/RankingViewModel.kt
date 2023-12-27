package isel.gomuku.screens.ranking

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.services.http.statistics.StatsServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.services.http.statistics.model.PlayerStats
import isel.gomuku.services.http.statistics.model.RankingModel
import isel.gomuku.services.http.statistics.model.Rankings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RankingViewModel(private val service: StatsServiceHttp):BaseViewModel() {
    var rankings: RankingModel? by mutableStateOf(null)
        private set
    var globalStatistics: GlobalStatistics? by mutableStateOf(null)
        private set

    var currentState : RankingMenuState by mutableStateOf(RankingMenuState.MENU)

    var nickname by  mutableStateOf("")

    var playerStats: PlayerStats? by mutableStateOf(null)

    fun editName (name: String) {
        nickname = name
    }

    fun getPlayerStats (id:Int) {
        safeCall {
            playerStats = service.getPlayerStats(id)
        }
    }
    fun search (listState: LazyListState, coroutineScope: CoroutineScope) {
        safeCall {
            coroutineScope.launch {
                val index = rankings?.rankings?.bestPlayers?.indexOfFirst { it.playerName == nickname }
                if (index != null && index != -1) {
                    listState.animateScrollToItem(index)
                } else if (index != -1) {

                }
            }
        }
    }

    fun changeStatsToShow (state: RankingMenuState) {
        currentState = state
    }
    fun getRankings () {
        safeCall {
            val currRankings = rankings
            if (currRankings == null) {
                rankings = service.getRankings("0")
            }
        }
    }
    fun getMoreRankings () {
        safeCall {
            val currRankings = rankings
            if ( currRankings?.nextPage != null) {
                val page = currRankings.nextPage.takeLastWhile { it.isDigit() }
                val res = service.getRankings(page)
                rankings = getMoreRankings(currRankings, res)
            }
        }
    }
    private fun getMoreRankings (currRankings: RankingModel, newRankings: RankingModel): RankingModel {
        val ranks = Rankings (
            bestPlayers = currRankings.rankings.bestPlayers + newRankings.rankings.bestPlayers,
            victories = currRankings.rankings.victories + newRankings.rankings.victories,
            mostGames = currRankings.rankings.mostGames + newRankings.rankings.mostGames,
            mostTime = currRankings.rankings.mostTime + newRankings.rankings.mostTime,
            playerDefeats = currRankings.rankings.playerDefeats + newRankings.rankings.playerDefeats,
        )
        return  RankingModel(
            rankings = ranks,
            prevPage = newRankings.prevPage,
            nextPage = newRankings.nextPage
        )
    }
    fun getGlobalStats () {
        safeCall {
            globalStatistics = service.getGlobalStats()
        }
    }
}

