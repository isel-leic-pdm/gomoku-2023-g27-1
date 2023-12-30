package isel.gomuku.screens.ranking

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.isel.gomokuApi.domain.model.statistcs.GlobalStatistics
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.http.statistics.StatsServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.utils.RedirectException
import isel.gomuku.services.UserService
import isel.gomuku.services.http.statistics.model.LeaderBoard
import isel.gomuku.services.http.statistics.model.PlayerStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RankingViewModel(private val service: StatsServiceHttp, userService: UserService):BaseViewModel() {
    var leaderBoard: LeaderBoard? by mutableStateOf(null)
        private set
    var globalStatistics: GlobalStatistics? by mutableStateOf(null)
        private set

    var currentState : RankingScreenState by mutableStateOf(RankingScreenState.MENU)

    var nickname by  mutableStateOf("")

    var playerStats: PlayerStats? by mutableStateOf(null)


    private var activeUser : LoggedUser? = null
    init {
        safeCall {
            activeUser = userService.getUser()
        }
    }

    fun editName (name: String) {
        nickname = name
    }
    //função que nao recebe nada como parametro
    private fun getUser(redirect: (Exception) -> Unit): LoggedUser {
        try {
            Log.d("Test","User stat is :$activeUser")
            if (activeUser == null) throw RedirectException()

        } catch (ex: RedirectException) {
            Log.d("Test", "Exception thrown")
            redirect(ex)

        }
        return activeUser ?: throw IllegalStateException("User must re login")
    }

    fun searchMyRank (listState: LazyListState, coroutineScope: CoroutineScope, redirect: (Exception) -> Unit ) {
        val user = getUser(redirect)
        search(listState, coroutineScope, name = user.name)
    }
    fun getPlayerStats (id:Int) {
        safeCall {
            playerStats = service.getPlayerStats(id)
            currentState = RankingScreenState.PLAYER_STATS
        }
    }
    fun search (listState: LazyListState, coroutineScope: CoroutineScope, name: String = nickname) {
        safeCall {
                val index = leaderBoard?.players?.indexOfFirst { it.playerName == name }
                if (index != null && index != -1) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index)
                    }
                } else  {
                    val currLeaderBoard  = leaderBoard
                    if ( currLeaderBoard?.nextPage != null ) {
                        val res = service.getRankings(currLeaderBoard.nextPage , name )
                        withContext(Dispatchers.Main) {
                            leaderBoard = getMorePlayers (currLeaderBoard, res )
                            }
                        val index2 = leaderBoard?.players?.indexOfFirst { it.playerName == name }
                        if (index2 != null && index2 != -1) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index2)
                            }
                        }
                    }
                }
        }
    }

    fun getRankings () {
        safeCall {
            val currLeaderBoard = leaderBoard
            val newLeaderBoard = service.getRankings(0, null)
            withContext(Dispatchers.Main) {
                if (currLeaderBoard == null) {
                    leaderBoard =  newLeaderBoard
                }
                currentState = RankingScreenState.LEADER_BOARD
            }
        }
    }
    fun getMoreRankings () {
        safeCall {
            val currLeaderBoard  = leaderBoard
            if ( currLeaderBoard?.nextPage != null ) {
                val res = service.getRankings(currLeaderBoard.nextPage , null )
                withContext(Dispatchers.Main) {
                    leaderBoard = getMorePlayers (currLeaderBoard, res )
                }
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
            withContext(Dispatchers.Main) {
                globalStatistics = service.getGlobalStats()
                currentState = RankingScreenState.GLOBAL_STATS
            }
        }
    }
}

