package isel.gomuku.screens.gameScreeens.remoteGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.annotation.SuppressLint

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import isel.gomuku.services.local.gameLogic.Board
import isel.gomuku.services.local.gameLogic.Player
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.gameScreeens.gatherInfo.GameVariants
import isel.gomuku.screens.gameScreeens.gatherInfo.OpeningRules
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import android.util.Log
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.UserService
import isel.gomuku.services.local.gameLogic.Position
import isel.gomuku.services.http.game.GameServiceHttp
import isel.gomuku.services.http.game.RejoinGame
import isel.gomuku.services.http.game.httpModel.AwaitingOpponent
import isel.gomuku.services.http.game.httpModel.GameDetails
import isel.gomuku.services.http.game.httpModel.GameEnded
import isel.gomuku.services.http.game.httpModel.GameRunning
import isel.gomuku.services.http.game.httpModel.GameStatus
import isel.gomuku.services.http.game.httpModel.LobbyClosed
import isel.gomuku.services.http.game.httpModel.PlayMade
import isel.gomuku.services.http.game.httpModel.UserInfo
import isel.gomuku.services.http.game.httpModel.WaitingOpponentPieces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//@Parcelize
//data class ParcelizedPosition private constructor(val lin: Int, val col: Int):Parcelable
@Parcelize
data class Plays(val pos: Position, val player: Player?) : Parcelable
@Parcelize
class RedirectException(override val message: String ="You must login once more" ) : Exception(), Parcelable {
}

data class Polling(val isPolling: Boolean, val reason : String? = null)
@SuppressLint("MutableCollectionMutableState")
class RemoteGameViewModel(
    private val saveHandle: SavedStateHandle,
    private val userService: UserService,
    private val gameService: GameServiceHttp
) : BaseViewModel() {

    companion object {
        val saveArgument = ""
    }

    var moves by mutableStateOf<MutableMap<Position, Player?>?>(null)

    var player: Player? by mutableStateOf(null)

    private var lobbyId: Int? by mutableStateOf(null)

    var poll by mutableStateOf(Polling(false))

    var isGameOver: Boolean by mutableStateOf(false)

    var winner: Boolean? by mutableStateOf(null)

    private var activeUser : LoggedUser? = null

    private var gameDetails : GameDetails? = null

    private var lastPlayedPosition : Position? = null

    var opponent: UserInfo? = null


    init {
        safeCall {
            activeUser = userService.getUser()
        }
        val data = saveHandle.get<Game?>(saveArgument)
        if (data != null) {
            moves = data.moves
            player = data.player
            lobbyId = data.lobbyId
        }

    }
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

    private suspend fun setGameState(gameState: GameStatus,user: LoggedUser,leaveScreen: (() -> Unit)? = null){
        when(gameState){
            is GameRunning -> {
                withContext(Dispatchers.IO){
                    if (lobbyId == null){
                        lobbyId = gameState.lobbyId
                        setServiceLobby(gameState.lobbyId)
                    }
                    if (player == null){
                        player = gameState.playerPiece
                    }
                    if (opponent == null){

                        opponent = gameState.opponent
                    }
                    if (moves == null){
                        moves = Board.startBoard(gameState.gridSize)
                        gameState.moves.forEach {
                            moves!![it.position] = it.goPiece
                        }
                    }

                    if (poll.isPolling == gameState.isPlayerTurn){
                        poll =
                            Polling(!gameState.isPlayerTurn,
                            if (gameState.isPlayerTurn == false)"Waiting for opponent play" else null)
                        if (gameState.isPlayerTurn){
                            gameState.moves.forEach {
                                moves!![it.position] = it.goPiece
                            }
                        }
                    }
                }
            }
            is GameEnded -> {
                withContext(Dispatchers.IO){
                    poll = Polling(false)
                    saveHandle[saveArgument] = null
                    moves = Board.startBoard(gameDetails!!.gridSize)
                    gameState.moves.forEach {
                        moves!![it.position] = it.goPiece
                    }
                    isGameOver = true
                    if (gameState.winner != null) {
                        winner = gameState.winner == user.id
                    }
                }
            }
            is AwaitingOpponent -> {
                withContext(Dispatchers.IO){
                    Log.d("Test","Board state is $moves")
                    if (moves == null){
                        moves = Board.startBoard(gameState.gridSize)
                        saveHandle[saveArgument] = Game(moves!!, gameState.lobbyId, null)
                    }


                    if (lobbyId == null){
                        lobbyId = gameState.lobbyId
                        setServiceLobby(gameState.lobbyId)
                    }
                    if (poll.isPolling == false) poll = Polling(true,"Waiting for adversary")
                }

            }
            is PlayMade -> {
                withContext(Dispatchers.IO){
                    moves?.put(lastPlayedPosition!!,player)
                    if (poll.isPolling == false) poll = Polling(true,"Waiting for opponent play")
                }
            }
            is LobbyClosed -> {
                if (leaveScreen != null){
                    leaveScreen()
                }
            }
            is WaitingOpponentPieces -> {
                if (poll.isPolling == false) poll = Polling(true,"Waiting for opponent play")
            }

            else -> {throw IllegalStateException("Unexpected game state")}
        }
    }

    fun startGame(
        gridSize: Int,
        variants: GameVariants,
        openingRules: OpeningRules,
        redirect: (Exception) -> Unit
    ) {
        if (gameDetails == null){
        safeCall {
            try {
                val user = getUser(redirect)
                val gameState =
                    gameService.startGame(gridSize, variants.name, openingRules.name, user.token)
                gameDetails = GameDetails(getServiceLobby()!!,gridSize,openingRules.name,variants.name)
                setGameState(gameState,user)
            } catch (ex: RejoinGame) {
                val user = getUser(redirect)
                val game = gameService.getGameDetails(user.token)
                if (gameDetails == null) {
                    gameDetails = game
                }
                Log.d("Test","Setting ID ${game.lobbyId}")
                setServiceLobby(game.lobbyId)
                if (lobbyId == null) {
                    withContext(Dispatchers.IO){
                        lobbyId = game.lobbyId
                    }
                }
                val gameState = gameService.getGameState(user.token)
                setGameState(gameState,user)
            }catch (ex: RedirectException){
                userService.logout()
                redirect(ex)
            }

        }
        }
    }




    private fun setServiceLobby(lobbyId: Int) {
            gameService.setLobbyId(lobbyId)
    }
    private fun getServiceLobby() = gameService.getLobbyId()

    fun play(pos: Position,redirect: (Exception) -> Unit) {
        safeCall {
            if (isGameOver) throw Exception("Game is already over")
            if(poll.isPolling == true) throw Exception("Not your turn")
            val user = getUser(redirect)
            val gameState = gameService.play(pos.lin, pos.col, user.token)
            lastPlayedPosition = Position(pos.lin,pos.col)
            setGameState(gameState,user)
        }
    }

    fun quit(redirect: (Exception) -> Unit, endActivity: () -> Unit) {
        safeCall {
            val user = getUser(redirect)
            val gameState = gameService.quitGame(user.token)
            saveHandle[saveArgument] = null
            setGameState(gameState,user,endActivity)
        }
    }

    suspend fun fetchState(redirect: (Exception) -> Unit) {
        safeCall {
                val user: LoggedUser = getUser(redirect)
                val info = gameService.getGameState(user.token)
                setGameState(info,user)
        }

    }
    fun canPlay(selectGame:() -> Unit,reLogin: (Exception) -> Unit) {
        safeCall {
        if (activeUser == null){

                val tempUser = userService.getUser()
                if (tempUser != null){
                    activeUser = tempUser
                }else reLogin(RedirectException())
            }
        }
        if (lobbyId == null){
            //TODO:See if its in running game and try to rejoin
            selectGame()
        }
    }

}

fun createPlayList(gridSize: Int): List<Plays> {
    return List(gridSize * gridSize) { index ->
        return@List Plays(Position(index / gridSize, index % gridSize), null)
    }
}


@Parcelize
data class Game(
    val moves: MutableMap<Position, Player?>,
    val lobbyId: Int,
    val player: Player?
) : Parcelable {}