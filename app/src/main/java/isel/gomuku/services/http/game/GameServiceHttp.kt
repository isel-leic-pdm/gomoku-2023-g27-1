package isel.gomuku.services.http.game

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import isel.gomuku.screens.utils.RedirectException
import isel.gomuku.services.GameService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.Problem
import isel.gomuku.services.http.RemoteSourceException
import isel.gomuku.services.http.game.httpModel.AwaitingOpponent
import isel.gomuku.services.http.game.httpModel.GameDetails
import isel.gomuku.services.http.game.httpModel.GameEnded
import isel.gomuku.services.http.game.httpModel.GameOpened
import isel.gomuku.services.http.game.httpModel.GameRunning
import isel.gomuku.services.http.game.httpModel.GameStatus
import isel.gomuku.services.http.game.httpModel.LobbyClosed
import isel.gomuku.services.http.game.httpModel.Move
import isel.gomuku.services.http.game.httpModel.PlayMade
import isel.gomuku.services.http.game.httpModel.UserInfo
import isel.gomuku.services.http.game.httpModel.WaitingOpponentPieces
import isel.gomuku.services.http.requestBody.GameOptionsBody
import isel.gomuku.services.http.requestBody.PlayBody
import isel.gomuku.services.local.gameLogic.Player
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class GameServiceHttp(
    private val requestBuilder: HttpRequest,
    private val gson: Gson,
    private val baseApiUrl: String
) : GameService {

    private var lobbyId: Int? = null // Guardado em remote game onde for chamada

    //Pode ser usado para polling
    private val gameUrl = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("game")
    }
    private val startGameURL = {
        gameUrl()
            .addPathSegment("start")
    }
    private val playUrl = {
        gameUrl()
            .addPathSegment("play")
            .addPathSegment("$lobbyId")
    }
    private val quitUrl = {
        gameUrl()
            .addPathSegment("quit")
            .addPathSegment("$lobbyId")
    }


    private val gameState = {
        gameUrl()
            .addPathSegment("$lobbyId")
            .addPathSegment("state")
    }
    private val gameInfo = {
        gameUrl()
            .addPathSegment("$lobbyId")
    }
    private val activeGame = {
        gameUrl()
            .addPathSegment("active-match")
    }
    private fun getAuth(auth: String):Map<String, String>{
        return mapOf("Authorization" to "Bearer $auth")
    }

    private suspend fun remoteRequest(request: Request, action:((Response) -> Unit)? = null){
        Log.d("Test","id was: $lobbyId")
        Log.d("Test","making request to : ${request.url}")
        try {
            requestBuilder.doRequest(request){
                if (action != null) {
                    action(it)
                }
            }
        }catch (jsonException:JsonSyntaxException){
            throw IllegalStateException("Fatal error:" + jsonException.message)
        }catch (remote: RemoteSourceException){
            val prob = gson.fromJson(remote.body?.string(), Problem::class.java)
            if (remote.status == 401){
                throw RedirectException()
            }
            if (prob.type != "basic" && prob.title == "Already in a game"){
                Log.d("Test",prob.toString())
                throw RejoinGame()
            }
            throw Exception(if (prob.detail != null)prob.title +":" + prob.detail else prob.title)
        }
    }

    fun setLobbyId(lobby: Int) {
        lobbyId = lobby
    }
    fun getLobbyId(): Int? = lobbyId

    override suspend fun play(line: Int, column: Int, auth: String): GameStatus {
        val play = PlayBody(line,column)
        val body = gson.toJson(play).toRequestBody("application/json".toMediaType())
        val request = requestBuilder.post(
            playUrl(),
            body,
            getAuth(auth)
        )
        var gameState : GameStatus? = null
        Log.d("Test","Starting game")
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),ApiGameDetails::class.java)
            gameState = when{
                dto.playMade != null -> dto.playMade
                dto.gameEnded != null -> {
                    dto.gameEnded.opponent = dto.opponent
                    dto.gameEnded
                }
                dto.lobbyClosed != null-> dto.lobbyClosed
                dto.awaitingOpponent != null -> dto.awaitingOpponent
                else ->  throw IllegalStateException("Unexpected server response")
            }
            Log.d("Test",dto.toString())
        }

        requireNotNull(gameState)
        return gameState as GameStatus

    }


    override suspend fun startGame(
        gridSize: Int,
        variants: String,
        openingRules: String,
        auth: String
    ): GameStatus {
        val gameOptions = GameOptionsBody(gridSize,openingRules,variants)
        val body = gson.toJson(gameOptions).toRequestBody("application/json".toMediaType())
        val request = requestBuilder.post(
            startGameURL(),
            body,
            mapOf("Authorization" to "Bearer $auth")
        )
        var gameState : GameStatus? = null
        Log.d("Test","Starting game")
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),ApiGameDetails::class.java)
            gameState = when{
                dto.awaitingOpponent != null -> {
                    lobbyId = dto.awaitingOpponent.lobbyId
                    dto.awaitingOpponent
                }
                dto.waitingOpponentPieces != null -> {
                    lobbyId = dto.waitingOpponentPieces.lobbyId
                    dto.waitingOpponentPieces.opponent = dto.opponent
                    dto.waitingOpponentPieces
                }
                else -> throw IllegalStateException("Unexpected server response")
            }
        }

        requireNotNull(gameState)
        return gameState as GameStatus

    }

    override suspend fun quitGame(auth: String): GameStatus {
        val request = requestBuilder.post(
            quitUrl(),
            headers = getAuth(auth)
        )
        var gameState : GameStatus? = null
        Log.d("Test","Starting game")
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),ApiGameDetails::class.java)
            gameState = when{
                dto.gameEnded != null -> {
                    dto.gameEnded.opponent = dto.opponent
                    dto.gameEnded
                }
                dto.lobbyClosed != null -> dto.lobbyClosed
                else -> throw IllegalStateException("Unexpected server response")
            }
        }
        requireNotNull(gameState)
        return gameState as GameStatus
    }

    override suspend fun getGameState(auth: String): GameStatus {
        val request = requestBuilder.get(
            gameState(),
            getAuth(auth)
        )
        var gameState : GameStatus? = null
        Log.d("Test","Starting game")
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),ApiGameDetails::class.java)
            gameState = when{
                dto.awaitingOpponent != null -> dto.awaitingOpponent
                dto.gameEnded != null -> {
                    dto.gameEnded.opponent = dto.opponent
                    dto.gameEnded
                }
                dto.gameRunning != null -> {
                     dto.gameRunning.opponent = dto.opponent
                    dto.gameRunning

                }
                dto.playMade != null -> dto.playMade
                dto.lobbyClosed != null -> dto.lobbyClosed
                else -> throw IllegalStateException("Unexpected server response")
            }
        }
        requireNotNull(gameState)
        return gameState as GameStatus
    }

    override suspend fun getGameDetails(auth: String): GameDetails {
        val request = requestBuilder.get(
            activeGame(),getAuth(auth)
        )
        var gameDetails :GameDetails? = null
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),ApiGameDetails::class.java)
            if (dto.game != null){
                gameDetails = dto.game
            }else{
                throw IllegalStateException("Unexpected server response")
            }
        }
        return gameDetails!!
    }


}

class RejoinGame : Exception() {

}

data class ApiGameDetails(
    val game : GameDetails,
    val awaitingOpponent: AwaitingOpponent,
    val gameRunning: GameRunning,
    val playMade: PlayMade,
    val gameEnded: GameEnded,
    val lobbyClosed : LobbyClosed,
    val gameOpened : GameOpened,
    val waitingOpponentPieces : WaitingOpponentPieces,
    val opponent:UserInfo
)

class GameStateReturn(
    val moves: List<Move>,
    val inOpponentOpening: Boolean,
    val winner: Int?,
    val hasGameEnded: Boolean
)
class GameStart(val id: Int, val player: Player)