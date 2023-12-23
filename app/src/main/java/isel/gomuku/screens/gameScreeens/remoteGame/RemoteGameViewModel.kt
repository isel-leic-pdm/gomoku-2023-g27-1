package isel.gomuku.screens.gameScreeens.remoteGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.annotation.SuppressLint

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import isel.gomuku.gameLogic.Board
import isel.gomuku.gameLogic.Player
import isel.gomuku.http.GameServiceHttp
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.gameScreeens.gatherInfo.GameVariants
import isel.gomuku.screens.gameScreeens.gatherInfo.OpeningRules
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import isel.gomuku.gameLogic.Position

//@Parcelize
//data class ParcelizedPosition private constructor(val lin: Int, val col: Int):Parcelable
@Parcelize
data class Plays(val pos: Position, val player: Player?) : Parcelable

@SuppressLint("MutableCollectionMutableState")
class RemoteGameViewModel(private val saveHandle: SavedStateHandle) : BaseViewModel() {

    companion object {
        val saveArgument = ""
    }

    var moves by mutableStateOf<MutableMap<Position, Player?>>(Board.startBoard(15)) //mutableStateOf(listOf<Plays>())

    var player: Player? by mutableStateOf(null)

    var lobbyId: Int? by mutableStateOf(null)

    //var board: Board by mutableStateOf(BoardRun(Board.startBoard(15), Player.WHITE))


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
        service: GameServiceHttp,
        authentication: String
    ) {
        safeCall {
            if (lobbyId != null) {
                setServiceLobby(service)
                throw Exception("In the middle of a game, reconnecting")
            }
            service.startGame(gridSize, variants.name, openingRules.name, authentication)

            moves = Board.startBoard(gridSize)

            /** Make startGame return lobby and player type*/
            saveHandle[saveArgument] = Game(moves, 0, Player.BLACK)
        }
    }


    private fun setServiceLobby(service: GameServiceHttp) {
        if (lobbyId != null)
            service.setLobbyId(lobbyId!!)
    }

    fun play(pos: Position, service: GameServiceHttp, authentication: String) {
        safeCall {
            service.play(pos.lin, pos.col, authentication)
            val movesCopy = HashMap(moves)
            movesCopy[pos] = player
            moves = movesCopy
            saveHandle[saveArgument] = Game(moves, lobbyId!!, player!!)
        }
    }

    fun quit(service: GameServiceHttp, authentication: String) {
        safeCall {
            service.quitGame(authentication)
            saveHandle[saveArgument] = null
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
    val player: Player
) : Parcelable {}