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
import isel.gomuku.services.UserService
import isel.gomuku.services.local.gameLogic.Position
import isel.gomuku.services.http.game.GameServiceHttp
import isel.gomuku.services.http.game.httpModel.GoPiece
import isel.gomuku.services.local.gameLogic.BoardRun

//@Parcelize
//data class ParcelizedPosition private constructor(val lin: Int, val col: Int):Parcelable
@Parcelize
data class Plays(val pos: Position, val player: Player?) : Parcelable

@SuppressLint("MutableCollectionMutableState")
class RemoteGameViewModel(
    private val saveHandle: SavedStateHandle,
    private val userService: UserService,
    private val gameService: GameServiceHttp
) : BaseViewModel() {

    companion object {
        val saveArgument = ""
    }

    var moves by mutableStateOf<MutableMap<Position, Player?>>(Board.startBoard(15))

    var player: Player? by mutableStateOf(null)

    private var lobbyId: Int? by mutableStateOf(null)

    var poll by mutableStateOf(false)


    init {
        val data = saveHandle.get<Game?>(saveArgument)
        if (data != null) {
            moves = data.moves
            player = data.player
            lobbyId = data.lobbyId
        }
    }


    fun startGame(
        gridSize: Int,
        variants: GameVariants,
        openingRules: OpeningRules,
    ) {
        safeCall {Log.d("Test", "User = ${userService.getUser()}")
            if (lobbyId != null) {
                setServiceLobby()
                throw Exception("In the middle of a game, reconnecting")
            }
            val token = userService.getUser()?.token!!
            val id = gameService.startGame(gridSize, variants.name, openingRules.name, token)
            lobbyId = id
            setServiceLobby()

            moves = Board.startBoard(gridSize)

            /** Make startGame return lobby and player type*/
            saveHandle[saveArgument] = Game(moves, id, Player.BLACK)
        }
        poll = true
    }


    private fun setServiceLobby() {
        if (lobbyId != null)
            gameService.setLobbyId(lobbyId!!)
    }

    fun play(pos: Position) {
        safeCall {
            val token = userService.getUser()?.token!!
            val info = gameService.play(pos.lin, pos.col, token)
            val movesCopy = HashMap(moves)
            movesCopy[pos] = player
            moves = movesCopy
            saveHandle[saveArgument] = Game(moves, lobbyId!!, player!!)
            if (info != null) {
                if (info.winner != null)
                    throw Exception("You have won this game")
                else throw Exception("This game ended in a draw")
            }
            poll = true
        }
    }

    fun quit() {
        safeCall {
            val token = userService.getUser()?.token!!
            gameService.quitGame(token)
            saveHandle[saveArgument] = null
        }
    }

    suspend fun fetchState() {
        val token = userService.getUser()?.token!!
        val id = userService.getUser()?.id!!
        val info = gameService.getGameState(token) ?: return
        val newMoves = HashMap(moves)
        if (moves.size < info.moves.size)
            for (move in info.moves)
                if (moves[move.position] == null)
                    newMoves[move.position] =
                        if (move.goPiece == GoPiece.BLACK) Player.BLACK else Player.WHITE

        moves = newMoves

        if (info.hasGameEnded) {
            poll = false

            if (info.winner == null)
                throw Exception("This game Ended in a draw")
            if (info.winner == id)
                throw Exception("You have won this game")
            throw Exception("You have lost this game")
        }

        saveHandle[saveArgument] = Game(moves, lobbyId!!, player!!)
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
    val player: Player
) : Parcelable {}