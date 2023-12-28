package isel.gomuku.screens.ranking

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.http.statistics.StatsServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.services.http.statistics.model.LeaderBoard
import isel.gomuku.services.http.statistics.model.PlayerStats
import isel.gomuku.services.http.statistics.model.RankingModel
import isel.gomuku.services.http.statistics.model.Rankings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RankingViewModel(private val service: StatsServiceHttp, ):BaseViewModel() {
    var leaderBoard: LeaderBoard? by mutableStateOf(null)
        private set
    var globalStatistics: GlobalStatistics? by mutableStateOf(null)
        private set

    var currentState : RankingScreenState by mutableStateOf(RankingScreenState.MENU)

    var nickname by  mutableStateOf("")

    var playerStats: PlayerStats? by mutableStateOf(null)

    fun editName (name: String) {
        nickname = name
    }
    //função que nao recebe nada como parametro

    fun test (getUser: () -> LoggedUser?, redirect: () -> Unit  ) {
        val user = getUser() ?: redirect()

    }

    fun getPlayerStats (id:Int) {
        safeCall {
            playerStats = service.getPlayerStats(id)
            currentState = RankingScreenState.PLAYER_STATS
        }
    }
    fun search (listState: LazyListState, coroutineScope: CoroutineScope) {
        safeCall {
            coroutineScope.launch {
                val index = leaderBoard?.players?.indexOfFirst { it.playerName == nickname }
                if (index != null && index != -1) {
                    listState.animateScrollToItem(index)
                } else  {
                    val currLeaderBoard  = leaderBoard
                    if ( currLeaderBoard?.nextPage != null ) {
                        val res = service.getRankings(currLeaderBoard.nextPage , nickname )
                        leaderBoard = getMorePlayers (currLeaderBoard, res )
                    }
                }
            }
        }
    }

    fun changeStatsToShow (state: RankingScreenState) {
        currentState = state
    }
    fun getRankings () {
        safeCall {
            val currRankings = leaderBoard
            if (currRankings == null) {
                leaderBoard = service.getRankings(0, null)
            }
            currentState = RankingScreenState.LEADER_BOARD
        }
    }

    fun getMoreRankings () {
        safeCall {
            val currLeaderBoard  = leaderBoard
            if ( currLeaderBoard?.nextPage != null ) {
                val res = service.getRankings(currLeaderBoard.nextPage , null )
                leaderBoard = getMorePlayers (currLeaderBoard, res )
            }
        }
    }

    private fun getMorePlayers (currLeaderBoard: LeaderBoard, newLeaderBoard: LeaderBoard ):LeaderBoard {
        return LeaderBoard (
            players = currLeaderBoard.players + newLeaderBoard.players,
            nextPage = newLeaderBoard.nextPage
        )
    }

    fun getGlobalStats () {
        safeCall {
            globalStatistics = service.getGlobalStats()
            currentState = RankingScreenState.GLOBAL_STATS
        }
    }
}

